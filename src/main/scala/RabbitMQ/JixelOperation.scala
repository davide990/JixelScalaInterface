package RabbitMQ

sealed trait JixelOperation {
  def operationName: String
}

case object notifyEvent extends JixelOperation {
  val operationName = "notifyEvent"
}

case object updateEventDescription extends JixelOperation {
  val operationName = "notifyEvent"
}

case object notifyReport extends JixelOperation {
  val operationName = "notifyEvent"
}