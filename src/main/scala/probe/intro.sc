//See https://stackoverflow.com/questions/17999409/scala-equivalent-of-javas-number
def sum[T](a: T, b: T, c: T)(implicit n: Numeric[T]): T = {
  import n._
  a + b + c
}
sum(1,2,3)
sum(1.0, 2.0, 3.0)
sum(1L, 2L, 3L)
