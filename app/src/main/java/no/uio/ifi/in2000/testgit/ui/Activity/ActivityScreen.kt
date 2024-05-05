package no.uio.ifi.in2000.testgit.ui.Activity

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import no.uio.ifi.in2000.testgit.R
import no.uio.ifi.in2000.testgit.ui.theme.DarkBlue
import no.uio.ifi.in2000.testgit.ui.theme.LightBlue


/*
TO DO
- navigasjon logikk mellom activities og visningstype
- home navigasjon med topbar backbutton (er popbackstack feil hvis vi kan ha
  mer navigasjon mellom
- avrundet bar over bottombar
 */
@Composable
fun ActivityScreen(chosenCity: String, navController: NavController) {
    //val nowCastUIState = activityScreenViewModel.nowCastUIState.collectAsState()
    //val metAlertsUIState = activityScreenViewModel.metAlertsUIState.collectAsState()

    val activities = listOf("sailing", "surfing", "swimming", "kayaking")
    var selectedButton by remember { mutableStateOf(activities[0]) }


    Column(modifier = Modifier
        .fillMaxSize()
        .background(DarkBlue)) {
        TopBarBy(navController, chosenCity)
        Row(modifier = Modifier.fillMaxWidth()) {
            GenerellInfo(chosenCity)
            Spacer(modifier = Modifier.weight(1f))
            ColorBar(value = 1)
        }


        Box(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            ExpandableIconButton(activities, selectedButton) { selectedButton = it }
        }
    }
}


@Composable
fun GenerellInfo(bynavn: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "By: $bynavn", color = Color.White)
        Text(text = "Linje 2", color = Color.White)
        Text(text = "Linje 3", color = Color.White)
        Text(text = "Linje 4", color = Color.White)
        Text(text = "Linje 5", color = Color.White)
        Text(text = "Linje 6", color = Color.White)
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarBy(navController: NavController, bynavn: String) {
    TopAppBar(
        title = {
            Text(text = bynavn, color = Color.White, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Image(painter = painterResource(id = R.drawable.ikon), contentDescription = "Tilbake", modifier = Modifier.size(50.dp))
            }
        },
        actions = {
            IconButton(onClick = { /* Favoritttrykk() som endrer  */ }) {
                if (false) {
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = "Favoritt",
                        modifier = Modifier
                            .size(50.dp),
                        tint = Color.White
                    )
                } else {
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = "Favoritt",
                        modifier = Modifier.size(50.dp),
                        tint = Color.Yellow
                    )
                }
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = DarkBlue),
        modifier = Modifier
            .zIndex(1f)
            .padding(4.dp)
    )
}
@Composable
fun ColorBar(value : Int) {
    var showDialog by remember { mutableStateOf(false) }
    require(value in 0..10) { "Verdien må være mellom 0 og 10" }
    val prosent = value * 10

    val colors = listOf(
        Color(0xFF008000), // Mørkegrønn
        Color(0xFF32CD32), // Grønn
        Color(0xFF7FFF00), // Lys grønn
        Color(0xFFADFF2F), // Gulgrønn
        Color(0xFFFFFF00), // Lys gul
        Color(0xFFFFD700), // Gul
        Color(0xFFFFA500), // Lys oransje
        Color(0xFFFF4500), // Oransje
        Color(0xFFB22222), // Rød
        Color(0xFFA52A2A)  // Mørkerød
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "$prosent %", color = Color.White)

        val total = 10
        val roundedTop = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)
        val roundedBottom = RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp)
        val none = RectangleShape

        for (i in 0 until total) {
            val currentShape = when {
                i == 0 && total - value == 1 -> roundedTop
                i == 0 -> roundedTop
                i == total - 1 -> roundedBottom
                else -> none
            }

            Box(
                modifier = Modifier
                    .size(width = 50.dp, height = 35.dp)
                    .clip(currentShape)
            ) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(if (i < total - value) Color.Gray else colors[i])
                        .border(1.dp, Color.White, currentShape)
                )
            }
        }

        IconButton(onClick = { showDialog = true }) {
            Icon(Icons.Default.Info, contentDescription = "Informasjon", tint = Color.White)
        }

        if (showDialog) {
            InfoPopUp { showDialog = false }
        }
    }
}
@Composable
fun InfoPopUp(onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = DarkBlue,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Her kan vi pumpe ut noe om hvorofr den er nede/oppe, ekstra info osv",
                    color = Color.White
                )
                Text(
                    text = "Vær oppmerksom på at anbefalinger er basert på værdata og egne algoritmer, og disse kan inneholde feil. Vi oppfordrer deg til å stole på dine egne observasjoner når du planlegger aktiviteter.",
                    color = Color.White
                )

                Button(
                    onClick = onDismissRequest,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightBlue,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Lukk")
                }
            }
        }
    }
}

@Composable
fun ExpandableIconButton(
    activities: List<String>,
    selectedActivity: String,
    onSelectedActivityChanged: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val iconSize: Dp = 60.dp
    val totalWidth = with(LocalDensity.current) {
        (activities.size - 1) * (iconSize.toPx() + 20.dp.toPx())
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clipToBounds()
    ) {
        IconButton(
            onClick = { expanded = !expanded },
            modifier = Modifier
                .size(iconSize)
                .clip(RoundedCornerShape(20.dp))
                .background(color = LightBlue)
                .zIndex(1f)
        ) {
            Icon(
                imageVector = getResourceId(selectedActivity),
                contentDescription = selectedActivity,
                tint = Color.White,
                modifier = Modifier.size(36.dp)
            )
        }

        AnimatedVisibility(
            visible = expanded,
            enter = slideInHorizontally(
                initialOffsetX = { -totalWidth.toInt() },
                animationSpec = tween(300)
            ),
            exit = slideOutHorizontally(
                targetOffsetX = { -totalWidth.toInt() },
                animationSpec = tween(300)
            )
        ) {
            Row {
                Spacer(modifier = Modifier.width(30.dp))
                activities.filter { it != selectedActivity }.forEachIndexed { index, activity ->
                    IconButton(
                        onClick = {
                            onSelectedActivityChanged(activity)
                            expanded = false
                        },
                        modifier = Modifier
                            .size(iconSize)
                            .clip(RoundedCornerShape(20.dp))
                            .background(color = LightBlue)
                    ) {
                        Icon(
                            imageVector = getResourceId(activity),
                            contentDescription = activity,
                            tint = Color.White,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                    if (index < activities.size - 2) {
                        Spacer(modifier = Modifier.width(30.dp))
                    }
                }
            }
        }
    }
}

fun getResourceId(activityName: String): ImageVector {
    return when (activityName) {
        "sailing" -> Icons.Filled.Info
        "surfing" -> Icons.Filled.Star
        "swimming" -> Icons.Filled.CheckCircle
        "kayaking" -> Icons.Filled.Build
        else -> Icons.Filled.Home
    }
}

