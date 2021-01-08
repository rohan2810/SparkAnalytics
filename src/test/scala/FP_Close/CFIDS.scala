package FP_Close


import scala.collection.mutable.ListBuffer

private[fpm] class CFIDS[T] extends Serializable {
  private var closed_buffer = ListBuffer[Set[T]]()

  def merge(other: CFIDS[T]): this.type = {
    other.extract().foreach { item =>
      add(item)
    }
    this
  }

  def add(itemset: Set[T]): this.type = {
    closed_buffer = closed_buffer.filterNot(x => x.subsetOf(itemset))
    if (closed_buffer.indexWhere(x => itemset.subsetOf(x)) == -1) {
      closed_buffer += itemset
    }
    this
  }

  def extract(): ListBuffer[Set[T]] = {
    closed_buffer
  }
}