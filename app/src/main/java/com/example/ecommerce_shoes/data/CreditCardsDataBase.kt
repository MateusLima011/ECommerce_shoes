package com.example.ecommerce_shoes.data

import com.example.ecommerce_shoes.domain.CreditCard

class CreditCardsDataBase {

    companion object {

        fun getItems() = listOf(
            CreditCard(
                "6502",
                "Visa",
                "Tony Stark"
            ),
            CreditCard(
                "9270",
                "Mastercard",
                "Scarlett Johansson"
            ),
            CreditCard(
                "661",
                "American Express",
                "Margot Robbie"
            ),
            CreditCard(
                "8738",
                "Visa",
                "Vivian Hernandez"
            ),
            CreditCard(
                "9011",
                "Visa",
                "Andrew Jackson"
            )
        )
    }
}