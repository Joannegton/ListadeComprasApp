@file:Suppress("NAME_SHADOWING")

package com.example.listadecompras.view

import EntradaTexto
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.ListaComprasTheme
import com.example.listadecompras.R
import com.example.listadecompras.componets.ListaComprasAppBar
import com.example.listadecompras.componets.ProdutosDaLista
import com.example.listadecompras.data.Item
import com.example.listadecompras.data.listaItensMercado
import com.example.listadecompras.model.ProdutoKg

/**
 * Componente principal que contém a tela da lista de compras.
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListadeComprasApp() {
    val listaCompras: MutableList<ProdutoKg> = mutableListOf(
        ProdutoKg("Arroz", 2, 2),
        ProdutoKg("Feijão", 3, 7),
    )

    var nomeProduto by remember { mutableStateOf("") }
    var quantidade by remember { mutableIntStateOf(0) }
    var valorProduto by remember { mutableIntStateOf(0) }

    // Variável de estado para armazenar as sugestões
    var sugestoes: List<Item> by remember { mutableStateOf(emptyList()) }
    var isSugestaoVisivel by remember { mutableStateOf(false) }

    Scaffold (
        topBar = {
            ListaComprasAppBar()
        }
    ){
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(65.dp))

            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                // Coluna contendo a entrada de texto para o nome do produto
                Column(verticalArrangement = Arrangement.Center) {
                    EntradaTexto(
                        value = nomeProduto,
                        onValueChange = {
                            nomeProduto = it
                            sugestoes = buscarSugestoes(nomeProduto)
                        },
                        label = "Produto",
                        modifier = Modifier.width(150.dp),
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )
                    if (nomeProduto !== "")
                        sugestoes.forEach { sugestao ->
                            Text(
                                text = sugestao.produto,
                                modifier = Modifier
                                    .clickable {
                                        nomeProduto = sugestao.produto
                                        sugestoes = emptyList()
                                    }
                                    .width(100.dp)
                                    .background(MaterialTheme.colorScheme.secondaryContainer)
                                    .padding(10.dp),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                }

                Spacer(modifier = Modifier.width(16.dp))

                EntradaTexto(
                    value = quantidade.toString(),
                    onValueChange = { quantidade = it.toInt() },
                    label = "Quantidade",
                    modifier = Modifier.width(100.dp),
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )

                Spacer(modifier = Modifier.width(16.dp))

                EntradaTexto(
                    value = valorProduto.toString(),
                    onValueChange = { valor ->
                        val novoValor = valor.toIntOrNull() ?: 0
                        valorProduto = novoValor
                    },
                    label = "Valor",
                    modifier = Modifier.width(100.dp),
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                )

                Spacer(modifier = Modifier.width(16.dp))

                Icon(
                    painter = painterResource(R.drawable.ic_done_24),
                    contentDescription = "Concluir",
                    modifier = Modifier.clickable {
                        listaCompras.add(ProdutoKg(nomeProduto, quantidade, valorProduto))
                        nomeProduto = ""
                        quantidade = 0
                        valorProduto = 0
                    }
                )

            }

            ProdutosDaLista(listaCompras)
        }
    }
}


/**
 * Função para buscar sugestões de produtos com base no texto digitado.
 */
fun buscarSugestoes(textoDigitado: String): List<Item> {
    return listaItensMercado.filter {
        it.produto.contains(textoDigitado, ignoreCase = true)
    }
}

/**
 * Preview da lista de produtos para visualização no Android Studio.
 */
@Preview(showBackground = true)
@Composable
fun ProdutosDaListaPreview() {
    ListaComprasTheme {
        ListadeComprasApp()
    }
}
