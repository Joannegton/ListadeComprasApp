package com.example.listadecompras.view

import EntradaTexto
import PromocaoProduto
import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.listadecompras.componets.MensagemErro
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
        ProdutoKg("Arroz", 2, 2.35),
        ProdutoKg("Feijão", 3, 7.35),
    )

    var nomeProduto by remember { mutableStateOf("") }
    var quantidade by remember { mutableStateOf("") }
    var valorProduto by remember { mutableStateOf("") }

    var temErro by remember { mutableStateOf(false) }
    var check by remember { mutableStateOf(false) }

    // Variável de estado para armazenar as sugestões
    var sugestoes: List<Item> by remember { mutableStateOf(emptyList()) }


    Scaffold (
        topBar = {ListaComprasAppBar()},
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(55.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 30.dp, 16.dp, 0.dp)
                    .height(83.dp),
                verticalAlignment = Alignment.Top,
                ) {
                // Coluna contendo a entrada de texto para o nome do produto
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    EntradaTexto(
                        value = nomeProduto,
                        onValueChange = {
                            nomeProduto = it
                            sugestoes = buscarSugestoes(nomeProduto)
                        },
                        label = "Produto",
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                        modifier = Modifier.width(150.dp)
                    )

                    LazyColumn {
                        items(sugestoes) { sugestao ->
                            Text(
                                text = sugestao.produto,
                                modifier = Modifier
                                    .clickable {
                                        nomeProduto = sugestao.produto
                                        sugestoes =
                                            emptyList() // limpar as sugestões ao selecionar
                                    }
                                    .width(150.dp)
                                    .height(20.dp)
                                    .background(MaterialTheme.colorScheme.secondaryContainer)
                                    .padding(horizontal = 10.dp)
                                    .animateContentSize(),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }

                }

                Spacer(modifier = Modifier
                    .let { if (check) it.padding(start = 200.dp) else it.padding(start = 16.dp)})

                if (!check) {
                        EntradaTexto(
                            value = quantidade,
                            onValueChange = { quantidade = it },
                            label = "Unidades",
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next,
                            modifier = Modifier.width(90.dp)
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        EntradaTexto(
                            value = valorProduto,
                            onValueChange = {
                                valorProduto = it
                            },
                            label = "Valor",
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done,
                            modifier = Modifier.width(70.dp)
                        )
                    }

                Spacer(modifier = Modifier.width(10.dp))

                Icon(
                    painter = painterResource(R.drawable.ic_done_100),
                    contentDescription = "Concluir",
                    modifier = Modifier
                        .clickable {
                            if ((nomeProduto.isNotBlank() && quantidade.isNotBlank() && valorProduto.isNotBlank()) || !check) {
                                try {
                                    val quantidadeInt = quantidade.toInt()
                                    val valorProdutoDouble = valorProduto.replace(",", ".").toDouble()
                                    listaCompras.add(
                                        ProdutoKg(
                                            nomeProduto,
                                            quantidadeInt,
                                            valorProdutoDouble
                                        )
                                    )
                                    nomeProduto = ""
                                    quantidade = ""
                                    valorProduto = ""
                                    temErro = false
                                } catch (e: Exception) {
                                    // Lidar com conversão de tipos inválidos
                                    temErro = true

                                }
                            } else {
                                // Mostrar mensagem de erro ou realizar outra ação adequada
                                temErro = true
                            }
                        }
                        .size(40.dp)
                        .padding(bottom = 12.dp)
                        .align(Alignment.CenterVertically)
                )
            }
            val variavelVazia: String = when {
                nomeProduto.isBlank() -> "um produto!"
                quantidade.isBlank() -> "uma quantidade!"
                valorProduto.isBlank() -> "um valor!"
                else -> ""
            }
            if (nomeProduto.isBlank() || quantidade.isBlank() || valorProduto.isBlank()) {
                MensagemErro("Insira $variavelVazia", 170, temErro)
            }

            if (check)
                PromocaoProduto(quantidade = quantidade, valor = valorProduto)

            Row (verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 20.dp)){
                Text(
                    text = "Produto em promoção?",
                    style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.padding(10.dp))
                Switch(
                    checked = check,
                    onCheckedChange = { isChecked ->
                        check = isChecked
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
private fun buscarSugestoes(textoDigitado: String): List<Item> {
     return if (textoDigitado.isNotEmpty()) {
        listaItensMercado.filter {
            it.produto.contains(textoDigitado, ignoreCase = true)
        }
    } else {
        emptyList()
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
