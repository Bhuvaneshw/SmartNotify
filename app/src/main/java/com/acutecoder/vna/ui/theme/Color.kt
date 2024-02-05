package com.acutecoder.services.ai.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Created by Bhuvaneshwaran
 *
 * 11:08 PM 08/01/2024
 * @author AcuteCoder
 */

open class Colors {
    open var isDark = false
    open val lightBlue = Color(0xFFDDE8FF)
    val white = Color.White
    val black = Color.Black
    open val light = Color.White
    open val primary = Color(0xFF0076d2)
    open val secondary = Color(0xffEFECFE)
    open val tertiary = Color(0xFF8121E0)
    open val statusBar = Color(0xffF2F5FF)
    open val navigationBar = Color(0xffF2F5FF)
    open val background = Color(0xffF2F5FF)
    open val dark = Color(0xff2E3A59)
    open val lightDark = Color(0xFF5B6785)
    open val primaryRipple = Color(0xFFC6CBE9)
    open val transparentEEE = Color(0xeeeeeeee)
}

object LightColors : Colors()

object DarkColors : Colors() {
    override var isDark = true
    override val lightBlue = Color(0xFF2B354E)
    override val light = Color(0xFF292E3C)
    override val primary = Color(0xFF367BB1)
    override val secondary = Color(0xFF252238)
    override val tertiary = Color(0xFFB967F8)
    override val statusBar = Color(0xFF1E222E)
    override val navigationBar = Color(0xFF1E222E)
    override val background = Color(0xFF1E222E)
    override val dark = Color(0xffffffff)
    override val lightDark = Color(0xFF8493BB)
    override val primaryRipple = Color(0xFFC0C0C0)
    override val transparentEEE = Color(0xee111111)
}
