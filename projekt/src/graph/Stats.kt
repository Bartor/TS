package graph

data class Stats(public var succeses: Double = 0.0, public var waits: Double = 0.0, public var collisions: Double = 0.0, public var tries: Double = 0.0)

data class StatyStats(public val full: Stats, public val half: Stats, public val quarter: Stats, public val star: Stats)