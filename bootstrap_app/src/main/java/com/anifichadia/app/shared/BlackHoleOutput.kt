package com.anifichadia.app.shared

import java.io.OutputStream
import java.io.PrintStream

class BlackHolePrintStream : PrintStream(BlackHoleOutputStream())

class BlackHoleOutputStream : OutputStream() {
    override fun write(b: Int) = NoOp()
}
