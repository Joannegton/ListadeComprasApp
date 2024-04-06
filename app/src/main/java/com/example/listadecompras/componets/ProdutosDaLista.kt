package com.example.listadecompras.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.listadecompras.model.ProdutoKg

@Composable
fun ProdutosDaLista(listaCompras: MutableList<ProdutoKg>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Box(
                modifier = modifier.fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer),
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Produto",
                        modifier = modifier.width(90.dp),
                        style = MaterialTheme.typography.labelSmall,
                    )
                    Text(text = "Quantidade",
                        modifier = modifier.width(90.dp),
                        style = MaterialTheme.typography.labelSmall,
                    )
                    Text(text = "Preço R$",
                        modifier = modifier.width(90.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.End
                    )
                }
            }
        }

        items(listaCompras) { produto ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondaryContainer)
            ) {
                ProdutoItem(produto, modifier = Modifier)
            }
        }
    }
}

@Composable
fun ProdutoItem(produto: ProdutoKg, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = produto.nome,
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier.width(100.dp)
        )
        Text(
            text = produto.quantidade.toString(),
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier.width(100.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = produto.preco.toString() ,
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier.width(100.dp),
            textAlign = TextAlign.End
        )
    }
}
