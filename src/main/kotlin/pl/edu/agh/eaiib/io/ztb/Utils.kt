package pl.edu.agh.eaiib.io.ztb


public inline fun <reified INNER> array2d(sizeOuter: Int, sizeInner: Int, noinline innerInit: (Int) -> INNER): Array<Array<INNER>>
        = Array(sizeOuter) { Array(sizeInner, innerInit) }

public fun array2dOfInt(sizeOuter: Int, sizeInner: Int): Array<IntArray> = Array(sizeOuter) { IntArray(sizeInner) }