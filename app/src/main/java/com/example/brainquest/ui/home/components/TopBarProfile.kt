package com.example.brainquest.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.brainquest.R
import com.example.brainquest.data.model.User
import com.example.brainquest.ui.theme.PurpleTheme
import com.example.brainquest.ui.theme.YellowThemeSecondary

@Composable
fun TopBarProfile(
    modifier: Modifier = Modifier,
    user: User?,
    onLogoutClicked: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
    ) {
        UserAvatar()

        Spacer(modifier = Modifier.width(12.dp))

        LevelProgressInfo(
            user = user,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(20.dp))

        IconButton(
            onClick = onLogoutClicked,
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Logout,
                contentDescription = "Sair da conta",
                tint = Color.Red.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
fun UserAvatar(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(52.dp)
            .background(
                color = Color(0xFFE0F7FA),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.avatar),
            contentDescription = "Avatar do usuário",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .border(
                    width = 3.dp,
                    color = PurpleTheme,
                    shape = CircleShape
                )
        )
    }
}

@Composable
fun LevelProgressInfo(modifier: Modifier = Modifier, user: User?) {
    val userXp = user?.totalScore ?: 0L

    val maxLevelXp = 100f
    val progress = (userXp.toFloat() / maxLevelXp).coerceIn(0f, 1f)

    Column(modifier = modifier) {
        Text(
            text = "Meu progresso",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold,
                color = Color.Black.copy(alpha = 0.7f),
                fontSize = 16.sp
            )
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .weight(1f)
                    .height(12.dp)
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = CircleShape
                    )
                    .clip(CircleShape),
                color = PurpleTheme,
                trackColor = Color.LightGray.copy(alpha = 0.4f),
                strokeCap = StrokeCap.Round
            )

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.Rounded.Star,
                contentDescription = "Ícone de estrela",
                tint = YellowThemeSecondary,
                modifier = Modifier
                    .size(20.dp)
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = "$userXp XP",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = YellowThemeSecondary,
                    fontSize = 16.sp
                )
            )
        }
    }
}