package com.bruno.clappy_bee.util


import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import clappybee.composeapp.generated.resources.Res
import clappybee.composeapp.generated.resources.chewy_regular
import org.jetbrains.compose.resources.Font

@Composable
fun ChewyFontFamily() = FontFamily(Font(Res.font.chewy_regular))