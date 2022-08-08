package com.savvy.app.logic_test

fun main() {

    /**
     * Test 1: Please write a function to detect that incoming string is palindrome or not
     */
    print("${"aka".isPalindrome()}")
    print("${"Hello".isPalindrome()}")
    print("${"Aka".isPalindrome()}")

    /**
     * Test 2: Please write a function to find the index that has the sum of left’s elements equal to the sum of right’s elements
     */
    print("${arrayOf(1,3,5,7,9).findIndexSumLeftESumRight()}")
    print("${arrayOf(3,5,6).findIndexSumLeftESumRight()}")
    print("${arrayOf(3,6,8,1,5,10,1,7).findIndexSumLeftESumRight()}")
    print("${arrayOf(1,2,3,4).findIndexSumLeftESumRight()}")
}

fun String.isPalindrome(): Boolean {
    var reverseText = ""
    for (i in this.length - 1 downTo 0) {
        reverseText += this[i]
    }
    return reverseText.equals(this, ignoreCase = true)
}

fun Array<Int>.findIndexSumLeftESumRight() : Int {
    val length = this.size

    if(length == 0) return -1
    if(length == 1) return 0

    //Create a new array with logic(From Left to Right): value of current index equals value of previous index plus value at index of origin array.
    val sumLeftArray = IntArray(length)
    sumLeftArray[0] = this[0]
    for (i in 1 until length) sumLeftArray[i] = sumLeftArray[i - 1] + this[i]

    //Create a new array with logic(From Right to Left): value of current index equals value of previous index plus value at index of origin array.
    val sumRightArray = IntArray(length)
    sumRightArray[length - 1] = this[length - 1]
    for (i in length - 2 downTo 0) sumRightArray[i] = sumRightArray[i + 1] + this[i]

    // Find the index
    for (i in 1 until length - 1) if (sumLeftArray[i] == sumRightArray[i]) return i

    return -1
}