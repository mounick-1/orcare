package com.simats.orcare.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.simats.orcare.data.Reminder
import com.simats.orcare.data.ReminderRepository
import com.simats.orcare.receiver.AlarmReceiver
import com.simats.orcare.ui.theme.*
import com.simats.orcare.ui.components.*
import kotlinx.coroutines.launch
import java.util.Calendar
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindersScreen(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val repository = remember { ReminderRepository(context) }
    val remindersList by repository.reminders.collectAsState(initial = emptyList())

    // Permission Launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (!isGranted) {
                Toast.makeText(context, "Notifications disabled. You won't receive reminders.", Toast.LENGTH_SHORT).show()
            }
        }
    )

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    var showDialog by remember { mutableStateOf(false) }
    var currentReminder by remember { mutableStateOf<Reminder?>(null) }
    var reminderTime by remember { mutableStateOf("") }

    if (showDialog && currentReminder != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Edit Time: ${currentReminder?.title}") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(
                         text = "Update the time for this reminder (HH:MM).",
                         style = MaterialTheme.typography.bodyMedium.copy(color = SlateMuted)
                    )
                    com.simats.orcare.ui.components.ORCareTextField(
                        value = reminderTime,
                        onValueChange = { reminderTime = it },
                        label = "Time (e.g. 08:00)"
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (reminderTime.isNotBlank() && reminderTime.matches(Regex("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$"))) {
                            val updatedWithTime = currentReminder!!.copy(time = reminderTime)
                            scope.launch {
                                repository.updateReminder(updatedWithTime)
                                if (updatedWithTime.isEnabled) {
                                    scheduleAlarm(context, updatedWithTime)
                                }
                            }
                            showDialog = false
                        } else {
                            Toast.makeText(context, "Invalid time format. Use HH:MM", Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Text("Save", color = PrimaryCoral)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel", color = SlateMuted)
                }
            },
            containerColor = SurfaceWhite,
            titleContentColor = TextPrimary,
            textContentColor = TextSecondary
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Daily Routine",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back",
                            tint = TextPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SurfaceWhite)
            )
        },
        containerColor = SurfaceWhite
    ) { paddingValues ->
        GradientBackground {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(bottom = 100.dp) // Space for bottom nav
            ) {
            // Info Card Header
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    shape = Shapes.large,
                    colors = CardDefaults.cardColors(containerColor = PrimaryCoral.copy(alpha = 0.08f)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryCoral.copy(alpha = 0.2f))
                ) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
                        Icon(Icons.Rounded.Info, contentDescription = null, tint = PrimaryCoral)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Customize your daily oral health routine. Toggle reminders on or off to stay consistent.",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = TextPrimary,
                                lineHeight = 20.sp
                            )
                        )
                    }
                }
            }

            // Reminders List Items
            val sortedReminders = remindersList.sortedBy { it.id }
            items(sortedReminders, key = { it.id }) { reminder ->
                ReminderListItem(
                    reminder = reminder,
                    onEdit = {
                        currentReminder = reminder
                        reminderTime = reminder.time
                        showDialog = true
                    },
                    onToggle = { isChecked ->
                        val updated = reminder.copy(isEnabled = isChecked)
                        scope.launch {
                            repository.updateReminder(updated)
                            if (isChecked) {
                                scheduleAlarm(context, updated)
                            } else {
                                cancelAlarm(context, updated)
                            }
                        }
                    }
                )
                
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    color = TextMuted.copy(alpha = 0.3f),
                    thickness = 0.5.dp
                )
                }
            }
        }
    }
}

@Composable
fun ReminderListItem(
    reminder: Reminder,
    onEdit: () -> Unit,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (reminder.isEnabled) SurfaceWhite.copy(alpha = 0.7f) else TextMuted.copy(alpha = 0.05f))
            .padding(horizontal = 24.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
            Surface(
                modifier = Modifier.size(44.dp),
                shape = Shapes.medium,
                color = if (reminder.isEnabled) reminder.color.copy(alpha = 0.1f) else TextMuted.copy(alpha = 0.3f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = reminder.icon,
                        contentDescription = null,
                        tint = if (reminder.isEnabled) reminder.color else TextSecondary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = reminder.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = if (reminder.isEnabled) TextPrimary else TextSecondary,
                        fontSize = 16.sp
                    )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Rounded.Schedule,
                        contentDescription = null,
                        tint = SlateMuted,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = reminder.time,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = TextSecondary,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onEdit, enabled = reminder.isEnabled) {
                Icon(
                    imageVector = Icons.Rounded.Edit, 
                    contentDescription = "Edit", 
                    tint = if (reminder.isEnabled) PrimaryCoral.copy(alpha = 0.7f) else TextMuted,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Switch(
                checked = reminder.isEnabled,
                onCheckedChange = onToggle,
                scale = 0.8f, // Slightly smaller switch to fit list style
                colors = SwitchDefaults.colors(
                    checkedThumbColor = SurfaceWhite,
                    checkedTrackColor = PrimaryCoral,
                    uncheckedThumbColor = TextSecondary,
                    uncheckedTrackColor = TextMuted.copy(alpha = 0.3f),
                    uncheckedBorderColor = Color.Transparent
                )
            )
        }
    }
}

// Extension to allow scaling the switch if needed, but standard Scale modifier works too
@Composable
fun Switch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    scale: Float = 1f,
    enabled: Boolean = true,
    colors: SwitchColors = SwitchDefaults.colors()
) {
    androidx.compose.material3.Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier.scale(scale),
        enabled = enabled,
        colors = colors
    )
}

@SuppressLint("ScheduleExactAlarm")
fun scheduleAlarm(context: Context, reminder: Reminder) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java).apply {
        putExtra("id", reminder.id)
        putExtra("title", reminder.title)
        putExtra("message", "It's time for your ${reminder.title} routine!")
    }
    
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        reminder.id,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val parts = reminder.time.split(":")
    if (parts.size == 2) {
        val hour = parts[0].toIntOrNull() ?: return
        val minute = parts[1].toIntOrNull() ?: return

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
        }

        if (calendar.timeInMillis < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1) // Schedule for tomorrow if time passed
        }

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        pendingIntent
                    )
                } else {
                     // Fallback or request permission - for now just normal set
                     alarmManager.setAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        pendingIntent
                    )
                }
            } else {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            }
            // Toast.makeText(context, "Alarm set for ${reminder.time}", Toast.LENGTH_SHORT).show()
        } catch (e: SecurityException) {
            e.printStackTrace()
            Toast.makeText(context, "Permission error setting alarm", Toast.LENGTH_SHORT).show()
        }
    }
}

fun cancelAlarm(context: Context, reminder: Reminder) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        reminder.id,
        intent,
        PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
    )

    if (pendingIntent != null) {
        alarmManager.cancel(pendingIntent)
    }
}
