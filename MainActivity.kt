package com.globalwalkietalkie.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class Channel(val name: String, val members: Int, val private: Boolean = true)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { GlobalWalkieApp() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlobalWalkieApp() {
    val context = LocalContext.current
    var language by remember { mutableStateOf("বাংলা") }
    var selected by remember { mutableStateOf("Global Team") }
    var talking by remember { mutableStateOf(false) }
    val channels = remember {
        listOf(
            Channel("Global Team", 12),
            Channel("Family Network", 5),
            Channel("Friends", 8)
        )
    }
    val bn = language == "বাংলা"

    MaterialTheme(colorScheme = darkColorScheme()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text("Global Walkie Talkie", fontWeight = FontWeight.Bold)
                            Text(if (bn) "Private voice channels" else "Private voice channels",
                                style = MaterialTheme.typography.labelSmall)
                        }
                    },
                    actions = {
                        TextButton(onClick = { language = if (bn) "English" else "বাংলা" }) {
                            Text(if (bn) "EN" else "বাংলা")
                        }
                    }
                )
            }
        ) { padding ->
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    Card(shape = RoundedCornerShape(20.dp)) {
                        Column(Modifier.padding(16.dp)) {
                            Text(if (bn) "আপনার চ্যানেল" else "Your channels",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold)
                            Text(if (bn) "প্রতি চ্যানেলে সর্বোচ্চ ১,০০০ সদস্যের লক্ষ্য" else "Designed for up to 1,000 members per channel")
                        }
                    }
                }
                items(channels) { channel ->
                    Card(
                        onClick = { selected = channel.name },
                        colors = CardDefaults.cardColors(
                            containerColor = if (selected == channel.name)
                                MaterialTheme.colorScheme.primaryContainer
                            else MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Row(Modifier.fillMaxWidth().padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically) {
                            Column(Modifier.weight(1f)) {
                                Text(channel.name, fontWeight = FontWeight.SemiBold)
                                Text("${channel.members} ${if (bn) "সদস্য" else "members"} • Private")
                            }
                            if (selected == channel.name) Text("✓")
                        }
                    }
                }
                item {
                    Text(if (bn) "কথা বলার চ্যানেল: $selected" else "Selected channel: $selected",
                        style = MaterialTheme.typography.titleMedium)
                    Button(
                        onClick = { talking = !talking },
                        modifier = Modifier.fillMaxWidth().height(88.dp),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Text(
                            if (talking) {
                                if (bn) "কথা বলা বন্ধ করুন" else "RELEASE TO STOP"
                            } else {
                                if (bn) "চেপে ধরে কথা বলুন" else "HOLD TO TALK"
                            },
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        if (bn) "এটি UI প্রোটোটাইপ। লাইভ অডিও এখনো যুক্ত করা হয়নি।" else "UI prototype only. Live audio is not connected yet.",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                item {
                    OutlinedButton(
                        onClick = {
                            val message = "Join my private Global Walkie Talkie channel: $selected. Install the app and request access."
                            val send = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, message)
                            }
                            context.startActivity(Intent.createChooser(send, if (bn) "Messenger-এ আমন্ত্রণ পাঠান" else "Share invitation"))
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(if (bn) "Messenger-এ আমন্ত্রণ পাঠান" else "Invite via Messenger")
                    }
                }
                item {
                    OutlinedButton(onClick = { talking = false }, modifier = Modifier.fillMaxWidth()) {
                        Text(if (bn) "চ্যানেল থেকে বের হন" else "Leave channel")
                    }
                }
            }
        }
    }
}
