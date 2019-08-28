package com.jennifer.andy.simpleeyes.utils

import android.net.Uri
import java.io.File


/**
 * Author:  andy.xwt
 * Date:    2019-08-28 20:04
 * Description:使用androidx.core:core-ktx中的工具类
 */

inline fun String.toUri(): Uri = Uri.parse(this)

/**
 * Creates a Uri from the given file.
 *
 * @see Uri.fromFile
 */
inline fun File.toUri(): Uri = Uri.fromFile(this)

/** Creates a [File] from the given [Uri]. */
inline fun Uri.toFile(): File = File(path)
