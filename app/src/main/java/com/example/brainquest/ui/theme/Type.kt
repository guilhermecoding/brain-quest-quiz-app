package com.example.brainquest.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.brainquest.R

var fontFunnelDisplayFamily = FontFamily(
    Font(R.font.funnel_display_regular, FontWeight.Normal),
    Font(R.font.funnel_display_bold, FontWeight.Bold),
    Font(R.font.funnel_display_medium, FontWeight.Medium),
    Font(R.font.funnel_display_semibold, FontWeight.SemiBold),
    Font(R.font.funnel_display_light, FontWeight.Light),
    Font(R.font.funnel_display_extrabold, FontWeight.ExtraBold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = fontFunnelDisplayFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = fontFunnelDisplayFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = fontFunnelDisplayFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)