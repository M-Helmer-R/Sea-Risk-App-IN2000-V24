package no.uio.ifi.in2000.testgit.ui.Activity

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.InvertColors
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Tsunami
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.Waves
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
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.selects.select
import no.uio.ifi.in2000.testgit.R
import no.uio.ifi.in2000.testgit.ui.BottomBar
import no.uio.ifi.in2000.testgit.ui.home.HomeUiState
import no.uio.ifi.in2000.testgit.ui.map.OceanForeCastUIState
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
fun ActivityScreen(
    chosenCity: String,
    lat: String?,
    lon: String?,
    navController: NavController,
    activityScreenViewModel: ActivityScreenViewModel = viewModel(factory = ActivityScreenViewModel.Factory)
) {
    val nowCastUIState = activityScreenViewModel.nowCastUIState.collectAsState()
    val oceanForeCastUIState =activityScreenViewModel.oceanForeCastUIState.collectAsState()
    //val metAlertsUIState = activityScreenViewModel.metAlertsUIState.collectAsState()

    val selectedActivityUIState = activityScreenViewModel.selectedActivityUIState.collectAsState()

    val activities = listOf("swimming", "sailing","surfing" , "kayaking")
    var selectedButton by remember { mutableStateOf(activities[0]) }
    val recommendationUIState = activityScreenViewModel.reccomendationUIState.collectAsState()
    val onEvent = activityScreenViewModel :: onEvent

    val acitivityUIState: AcitivityUIState by activityScreenViewModel.acitivityUIState.collectAsState()


    Column(modifier = Modifier
        .fillMaxSize()
        .background(DarkBlue)) {
        TopBarBy(
            navController = navController,
            bynavn = chosenCity,
            lat = lat,
            lon = lon,
            onEvent = onEvent,
            acitivityUIState = acitivityUIState,
        )
        recommendationUIState.value.level?.let { ReccomendationBox(value = it, selectedActivityUIState.value) }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(end = 0.dp)) {
            GeneralInfo(chosenCity, lat, lon, nowCastUIState.value, oceanForeCastUIState.value)
            Spacer(modifier = Modifier.weight(1f))
            recommendationUIState.value.level?.let { ColorBar(value = it, selectedActivityUIState.value) }
        }
        Spacer(Modifier.weight(1f))
        ExpandableIconButton(activities, selectedButton, activityScreenViewModel) { selectedButton = it }
        BottomBar(navController, currentRoute = "Aktivitetsscreen")
    }
}


@Composable
fun ReccomendationBox(value: Int, selectedActivityUIState: selectedActivityUIState) {
    val prosent = value * 10
    val activityRecommendation = when {
        prosent in 0..30 -> "Lav anbefaling. Det kan være best å velge en annen aktivitet eller dag."
        prosent in 31..50 -> "Dårlig anbefaling. Forholdene er akseptable, men vær forsiktig."
        prosent in 51..70 -> "OK anbefaling. Forholdene ser OK ut for denne aktiviteten."
        prosent in 71..100 -> "Sterk anbefaling. Dette er et flott tidspunkt for aktiviteten!"
        else -> "Ugyldig prosentverdi"
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, top = 0.dp, end = 30.dp, bottom = 10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(LightBlue)
            .border(2.dp, Color.White, RoundedCornerShape(10.dp))
            .height(110.dp)
    ) {
        Text(
            text = "Anbefaling for ${selectedActivityUIState.selectedactivity }: $prosent %   - $activityRecommendation",
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun GeneralInfo(bynavn: String, lat: String?, lon: String?, nowCastUIState: NowCastUIState, oceanForeCastUIState: OceanForeCastUIState) {
    var showPopup by remember { mutableStateOf(false) }
    var popupContent by remember { mutableStateOf("") }


    Box(
        modifier = Modifier
            .padding(start = 30.dp, top = 35.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(LightBlue)
            .border(2.dp, Color.White, RoundedCornerShape(10.dp))
            .width(240.dp)
            .height(345.dp)
    ) {
        Column(
            modifier = Modifier.padding(25.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Værinformasjon:", color = Color.White, fontSize = 20.sp)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Thermostat,
                    contentDescription = "Air Temperature",
                    tint = Color.White,
                    modifier = Modifier.clickable {
                        popupContent = "Temperatur: ${nowCastUIState.nowCastData?.airTemperature} C"
                        showPopup = true
                    }
                )
                Text(text = "${nowCastUIState.nowCastData?.airTemperature} C", color = Color.White, fontSize = 30.sp)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.rainy),
                    contentDescription = "Precipitation Rate",
                    tint = Color.White,
                    modifier = Modifier.clickable {
                        popupContent = "Nedbør: ${nowCastUIState.nowCastData?.precipitationRate} mm/h"
                        showPopup = true
                    }
                )
                Text(text = "${nowCastUIState.nowCastData?.precipitationRate} mm/h", color = Color.White, fontSize = 20.sp)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Air,
                    contentDescription = "Wind Speed",
                    tint = Color.White,
                    modifier = Modifier.clickable {
                        popupContent = "Vindhastighet: ${nowCastUIState.nowCastData?.windSpeed} m/s"
                        showPopup = true
                    }
                )
                Text(text = "${nowCastUIState.nowCastData?.windSpeed} m/s", color = Color.White, fontSize = 20.sp)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "", color = Color.White, fontSize = 20.sp)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Vanninformasjon:", color = Color.White, fontSize = 20.sp)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.WaterDrop,
                    contentDescription = "Sea Water Temperature",
                    tint = Color.White,
                    modifier = Modifier.clickable {
                        popupContent = "Vanntemperatur: ${oceanForeCastUIState.oceanDetails?.seaWaterTemperature} C"
                        showPopup = true
                    }
                )
                Text(text = "${oceanForeCastUIState.oceanDetails?.seaWaterTemperature} C", color = Color.White, fontSize = 30.sp)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Tsunami,
                    contentDescription = "Sea Surface Wave Height",
                    tint = Color.White,
                    modifier = Modifier.clickable {
                        popupContent = "Bølgehøyde: ${oceanForeCastUIState.oceanDetails?.seaSurfaceWaveHeight} m"
                        showPopup = true
                    }
                )
                Text(text = "${oceanForeCastUIState.oceanDetails?.seaSurfaceWaveHeight} m", color = Color.White, fontSize = 20.sp)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Waves,
                    contentDescription = "Sea Water Speed",
                    tint = Color.White,
                    modifier = Modifier.clickable {
                        popupContent = "Strøm:: ${oceanForeCastUIState.oceanDetails?.seaWaterSpeed} m/s"
                        showPopup = true
                    }
                )
                Text(text = "${oceanForeCastUIState.oceanDetails?.seaWaterSpeed} m/s", color = Color.White, fontSize = 20.sp)
            }
        }
    }

    if (showPopup) {
        InfoPopUpWithString(content = popupContent, onDismissRequest = { showPopup = false })
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarBy(
    navController: NavController,
    bynavn: String,
    lat: String?,
    lon: String?,
    onEvent : (ActivityEvent) -> Unit,
    acitivityUIState: AcitivityUIState,
) {
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
            onEvent(ActivityEvent.CheckFavorite(name = bynavn))
            IconButton(
                onClick = {
                    if (!acitivityUIState.favorite) {
                        Log.w("ActivityScreenViewModel", "adding")
                        onEvent(
                            ActivityEvent.AddFavorite(
                                name = bynavn,
                                lat =  lat ?: "",
                                lon = lon ?: "")
                        )
                    }
                }
            ) {
                if (acitivityUIState.favorite){
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = "Favoritt",
                        modifier = Modifier
                            .size(50.dp),
                        tint = Color.Yellow
                    )
                } else {
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = "Favoritt",
                        modifier = Modifier.size(50.dp),
                        tint = Color.White
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
fun ColorBar(value: Int, selectedActivityUIState: selectedActivityUIState) {
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

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Vi anbefaler ${selectedActivityUIState.selectedactivity}:      $prosent % ",
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

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
    activityScreenViewModel: ActivityScreenViewModel,
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
                painter = painterResource(
                    getResourceId(selectedActivity)),
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
                            activityScreenViewModel.changeActivity(activity)
                            activityScreenViewModel.changeReccomendationBar(activity)
                            Log.i("ActivityScreen", "Activity selected: $activity")
                            expanded = false
                        },
                        modifier = Modifier
                            .size(iconSize)
                            .clip(RoundedCornerShape(20.dp))
                            .background(color = LightBlue)
                    ) {
                        Icon(
                            painter = painterResource(getResourceId(activity)),
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


fun getResourceId(activityName: String): Int {
    return when (activityName) {
        "sailing" ->  R.drawable.sailboaticon
        "surfing" -> R.drawable.surfingicon
        "swimming" -> R.drawable.swimmingicon
        "kayaking" -> R.drawable.kayakicon
        else -> 0
    }
}

@Composable
fun InfoPopUpWithString(content: String, onDismissRequest: () -> Unit) {
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
                    text = content,
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



