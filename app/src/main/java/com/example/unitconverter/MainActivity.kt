package com.example.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unitconverter.ui.theme.UnitConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnitConverterTheme {
                    UnitConverter()
            }
        }
    }
}

@Composable
fun UnitConverter() {
    var inputValue by remember {
       mutableStateOf("0")
    }
    var outputValue by remember {
        mutableStateOf("0")
    }
    var fromUnit by remember {
        mutableStateOf("Milimeter")
    }
    var toUnit by remember {
        mutableStateOf("Milimeter")
    }
    var fromSelectOpen by remember {
        mutableStateOf(false)
    }
    var toSelectOpen by remember {
        mutableStateOf(false)
    }

    fun convertUnit(){
        var inputVal = if (inputValue.isEmpty()) 0.0 else inputValue.toDouble()
        var outputVal: Double

        val baseUnitValue = when(fromUnit) {
            "Milimeter" -> inputVal
            "Centimeter" -> inputVal * 10
            "Meter" -> inputVal * 1000
            "Kilometer" -> inputVal * 1000000
            else -> throw IllegalArgumentException("Invalid fromUnit: $fromUnit")
        }

        outputVal =  when(toUnit) {
            "Milimeter" -> baseUnitValue
            "Centimeter" -> baseUnitValue / 10
            "Meter" -> baseUnitValue / 1000
            "Kilometer" -> baseUnitValue / 1000000
            else -> throw IllegalArgumentException("Invalid toUnit: $toUnit")
        }

        outputValue = outputVal.toString()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(text = "Unit Converter")
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = inputValue, onValueChange = {newValue ->
            if (newValue.isEmpty() || newValue.toDoubleOrNull() != null) {
                inputValue = newValue
            }
            convertUnit()
        }, label={ Text(text = "Enter Value")})
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Box {
               Button(onClick = { fromSelectOpen = !fromSelectOpen }) {
                  Text(text = fromUnit)
                   Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "arrow dropdown" )
               }
                DropdownMenu(expanded = fromSelectOpen, onDismissRequest = { fromSelectOpen = false }) {
                    DropdownMenuItem(text = { Text(text = "Milimeter") }, onClick = { fromUnit = "Milimeter"; convertUnit(); fromSelectOpen = false })
                    DropdownMenuItem(text = { Text(text = "Centimeter") }, onClick = { fromUnit = "Centimeter" ; convertUnit(); fromSelectOpen = false })
                    DropdownMenuItem(text = { Text(text = "Meter") }, onClick = { fromUnit = "Meter" ; convertUnit();fromSelectOpen = false })
                    DropdownMenuItem(text = { Text(text = "Kilometer") }, onClick = { fromUnit = "Kilometer" ; convertUnit();fromSelectOpen = false })
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box {
                Button(onClick = { toSelectOpen = !fromSelectOpen }) {
                    Text(text = toUnit)
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "arrow dropdown" )
                }
                DropdownMenu(expanded = toSelectOpen, onDismissRequest = { toSelectOpen = false }) {
                    DropdownMenuItem(text = { Text(text = "Milimeter") }, onClick = { toUnit = "Milimeter"; convertUnit();toSelectOpen = false })
                    DropdownMenuItem(text = { Text(text = "Centimeter") }, onClick = { toUnit = "Centimeter" ; convertUnit();toSelectOpen = false })
                    DropdownMenuItem(text = { Text(text = "Meter") }, onClick = { toUnit = "Meter" ; convertUnit();toSelectOpen = false })
                    DropdownMenuItem(text = { Text(text = "Kilometer") }, onClick = { toUnit = "Kilometer" ; convertUnit();toSelectOpen = false })
                }
            }

        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "$inputValue $fromUnit = $outputValue $toUnit")
    }

}

@Preview(showBackground = true)
@Composable
fun UnitConverterPreview() {
    UnitConverter()
}