package com.savvy.app.logic_test

fun main() {

    /**
     * Test 1: Please write a function to detect that incoming string is palindrome or not
     */
    print("".detectPalindrome())
    print("a".detectPalindrome())
    print("aka".detectPalindrome())
    print("Aka".detectPalindrome())
    print("Hello".detectPalindrome())

    /**
     * Test 2: Please write a function to find the index that has the sum of left’s elements equal to the sum of right’s elements
     */
    print(arrayOf<Int>().detectIndexSumLeftESumRight())
    print(arrayOf(3,5,6).detectIndexSumLeftESumRight())
    print(arrayOf(1,2,3,4).detectIndexSumLeftESumRight())
    print(arrayOf(1,3,5,7,9).detectIndexSumLeftESumRight())
    print(arrayOf(3,6,8,1,5,10,1,7).detectIndexSumLeftESumRight())
}

fun String.detectPalindrome(): String {
    if (this.isEmpty()) {
        return displayPalindromeResult(this, true)
    }
    var reverseText = ""
    for (i in this.length - 1 downTo 0) {
        reverseText += this[i]
    }
    return displayPalindromeResult(this, reverseText.equals(this, ignoreCase = true))
}

fun displayPalindromeResult(text: String, isPalindrome: Boolean): String {
    return if (isPalindrome) {
        "$text is a palindrome"
    } else {
        "$text isn't a palindrome"
    }
}

fun Array<Int>.detectIndexSumLeftESumRight() : String {
    val length = this.size

    if (length == 0) return displayIndexResult(-1)
    if (length == 1) return displayIndexResult(0)

    //Create a new array with logic(From Left to Right): value of current index equals value of previous index plus value at index of origin array.
    val sumLeftArray = IntArray(length)
    sumLeftArray[0] = this[0]
    for (i in 1 until length) sumLeftArray[i] = sumLeftArray[i - 1] + this[i]

    //Create a new array with logic(From Right to Left): value of current index equals value of previous index plus value at index of origin array.
    val sumRightArray = IntArray(length)
    sumRightArray[length - 1] = this[length - 1]
    for (i in length - 2 downTo 0) {
        sumRightArray[i] = sumRightArray[i + 1] + this[i]
        if (sumLeftArray[i] == sumRightArray[i]) return displayIndexResult(i)
    }

    return displayIndexResult(-1)
}

fun displayIndexResult(index: Int): String {
    return if(index == -1){
        "index not found"
    }else{
        "middle index is $index"
    }
}