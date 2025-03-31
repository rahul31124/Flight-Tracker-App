package com.example.airvibes


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.airvibes.databinding.ActivitySplashBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Preview(showBackground = true)
@Composable
fun AirVibesCard() {

    Box(

        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)

        ,
        contentAlignment = Alignment.Center
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            shape = RoundedCornerShape(20.dp),
            backgroundColor = Color.White,
            elevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Image(
                    painter = painterResource(id = R.drawable.radrrr),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(70.dp)
                        .padding(top = 5.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.width(8.dp))


                Text(
                    text = "AIR ",
                    fontSize = 38.sp,
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.merriweathersans_semibold))
                )

                Text(
                    text = "VIBES",
                    fontSize = 38.sp,
                    color = colorResource(id = R.color.MediumBlue),
                    fontFamily = FontFamily(Font(R.font.merriweathersans_semibold))
                )
            }
        }
    }
}



class SplashActivity : AppCompatActivity() {


    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var webCategoryViewModel: WebCategoryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContent {
            AirVibesCard()
        }



        Handler().postDelayed({


                startActivity(Intent(this, LoginActivity::class.java))
                finish()

        }, 3000)



        categoryViewModel = CategoryViewModel(application)
        webCategoryViewModel=WebCategoryViewModel(application)
        CoroutineScope(Dispatchers.Main).launch {
            populateDatabase(applicationContext, categoryViewModel,webCategoryViewModel)

        }

    }
}


