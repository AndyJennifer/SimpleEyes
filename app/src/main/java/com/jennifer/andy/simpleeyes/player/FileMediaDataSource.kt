package com.jennifer.andy.simpleeyes.player

import tv.danmaku.ijk.media.player.misc.IMediaDataSource
import java.io.File
import java.io.IOException
import java.io.RandomAccessFile


/**
 * Author:  andy.xwt
 * Date:    2020/3/10 13:29
 * Description:
 */

class FileMediaDataSource(file: File) : IMediaDataSource {

    private var mFile: RandomAccessFile = RandomAccessFile(file, "r")
    var mFileSize = mFile.length()

    @Throws(IOException::class)
    override fun readAt(position: Long, buffer: ByteArray?, offset: Int, size: Int): Int {
        if (mFile.filePointer != position) mFile.seek(position)
        return if (size == 0) 0 else mFile.read(buffer, 0, size)
    }

    @Throws(IOException::class)
    override fun getSize(): Long {
        return mFileSize
    }

    @Throws(IOException::class)
    override fun close() {
        mFileSize = 0
        mFile.close()
    }

}