package com.example.debugger.uicommon

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.debugger.R
import com.example.debugger.data.Analysis

@Composable
fun ItemsCard(
    item: Analysis,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Card(
        modifier
            .clickable {
                onClick()
            },
        //    elevation = 5.dp,
        shape = RoundedCornerShape(5.dp),

        ) {
        Row(modifier = Modifier.fillMaxSize()) {
            item.image?.let {
                Image(
                    painter = it,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(CircleShape)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = item.title,
                    modifier = Modifier.weight(1.0f),
                    fontSize = 15.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = "",
                    modifier = Modifier.weight(1.0f),
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }
        }

    }
}