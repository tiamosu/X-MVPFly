package com.xia.fly.utils

import com.blankj.utilcode.util.CloseUtils
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.util.*
import java.util.zip.*

/**
 * 处理压缩和解压的工具类
 *
 * @author xia
 * @date 2018/9/14.
 */
@Suppress("unused")
object ZipHelper {

    /**
     * zlib decompress 2 String
     *
     * @param bytesToDecompress
     * @param charsetName
     * @return
     */
    @JvmStatic
    @JvmOverloads
    fun decompressToStringForZlib(bytesToDecompress: ByteArray, charsetName: String = "UTF-8"): String? {
        val bytesDecompressed = decompressForZlib(bytesToDecompress)
        var returnValue: String? = null
        try {
            returnValue = String(
                    bytesDecompressed!!,
                    0,
                    bytesDecompressed.size,
                    charset(charsetName)
            )
        } catch (uee: UnsupportedEncodingException) {
            uee.printStackTrace()
        }
        return returnValue
    }

    /**
     * zlib decompress 2 byte
     *
     * @param bytesToDecompress
     * @return
     */
    @JvmStatic
    fun decompressForZlib(bytesToDecompress: ByteArray): ByteArray? {
        var returnValues: ByteArray? = null
        val inflater = Inflater()
        val numberOfBytesToDecompress = bytesToDecompress.size
        inflater.setInput(bytesToDecompress, 0, numberOfBytesToDecompress)


        var numberOfBytesDecompressedSoFar = 0
        val bytesDecompressedSoFar = ArrayList<Byte>()

        try {
            while (!inflater.needsInput()) {
                val bytesDecompressedBuffer = ByteArray(numberOfBytesToDecompress)
                val numberOfBytesDecompressedThisTime = inflater.inflate(bytesDecompressedBuffer)
                numberOfBytesDecompressedSoFar += numberOfBytesDecompressedThisTime
                for (b in 0 until numberOfBytesDecompressedThisTime) {
                    bytesDecompressedSoFar.add(bytesDecompressedBuffer[b])
                }
            }

            returnValues = ByteArray(bytesDecompressedSoFar.size)
            for (b in returnValues.indices) {
                returnValues[b] = bytesDecompressedSoFar[b]
            }
        } catch (dfe: DataFormatException) {
            dfe.printStackTrace()
        }

        inflater.end()
        return returnValues
    }

    /**
     * zlib compress 2 byte
     *
     * @param bytesToCompress
     * @return
     */
    @JvmStatic
    fun compressForZlib(bytesToCompress: ByteArray): ByteArray {
        val deflater = Deflater()
        deflater.setInput(bytesToCompress)
        deflater.finish()

        val bytesCompressed = ByteArray(java.lang.Short.MAX_VALUE.toInt())
        val numberOfBytesAfterCompression = deflater.deflate(bytesCompressed)
        val returnValues = ByteArray(numberOfBytesAfterCompression)

        System.arraycopy(
                bytesCompressed,
                0,
                returnValues,
                0,
                numberOfBytesAfterCompression
        )

        return returnValues
    }

    /**
     * zlib compress 2 byte
     *
     * @param stringToCompress
     * @return
     */
    @JvmStatic
    fun compressForZlib(stringToCompress: String): ByteArray? {
        var returnValues: ByteArray? = null

        try {
            returnValues = compressForZlib(stringToCompress.toByteArray(charset("UTF-8")))
        } catch (uee: UnsupportedEncodingException) {
            uee.printStackTrace()
        }
        return returnValues
    }

    /**
     * gzip compress 2 byte
     *
     * @param string
     * @return
     * @throws IOException
     */
    @JvmStatic
    fun compressForGzip(string: String): ByteArray? {
        var os: ByteArrayOutputStream? = null
        var gos: GZIPOutputStream? = null
        try {
            os = ByteArrayOutputStream(string.length)
            gos = GZIPOutputStream(os)
            gos.write(string.toByteArray(charset("UTF-8")))
            return os.toByteArray()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            CloseUtils.closeIOQuietly(gos)
            CloseUtils.closeIOQuietly(os)
        }
        return null
    }

    /**
     * gzip decompress 2 string
     *
     * @param compressed
     * @param charsetName
     * @return
     */
    @JvmStatic
    @JvmOverloads
    fun decompressForGzip(compressed: ByteArray, charsetName: String = "UTF-8"): String? {
        val bufferSize = compressed.size
        var gis: GZIPInputStream? = null
        var `is`: ByteArrayInputStream? = null
        try {
            `is` = ByteArrayInputStream(compressed)
            gis = GZIPInputStream(`is`, bufferSize)
            val string = StringBuilder()
            val data = ByteArray(bufferSize)
            var bytesRead: Int
            do {
                bytesRead = gis.read(data)
                if (bytesRead == -1) {
                    break
                }
                string.append(String(data, 0, bytesRead, charset(charsetName)))
            } while (true)
            return string.toString()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            CloseUtils.closeIOQuietly(gis)
            CloseUtils.closeIOQuietly(`is`)
        }
        return null
    }
}
