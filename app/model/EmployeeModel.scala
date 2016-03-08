package model

import com.google.inject.ImplementedBy
import org.joda.time.format._
import scala.collection.mutable.ListBuffer


case class Employee(id:Int, name:String, dateOfBirth:String, dateOfJoining:String, designation:String)

case class Dashboard(name:String)

@ImplementedBy(classOf[EmployeeModel])
trait EmployeeModelService{
  def addEmployee(id:Int,name:String,dateOfBirth:String,dateOfJoining:String,designation:String):Int
  def showAll():List[Employee]
  def getEmployee(id:Int):List[Employee]
  def updateEmployee(id:Int,name:String,dateOfBirth:String,dateOfJoining:String,designation:String):Int
  def searchName(name:String):List[Employee]
  def deleteEmployee(id:Int):Int
}

class EmployeeModel extends EmployeeModelService{

  val format = DateTimeFormat.forPattern("dd-MM-yyyy")

  val employeeList:ListBuffer[Employee]= ListBuffer(
    Employee(1,"Sonu Mehrotra","25-10-1992","21-01-2016","Trainee"),
    Employee(2,"Rishabh Verma","23-02-1988","21-01-2016","Trainee"),
    Employee(3,"Deepti Bhardwaj","21-10-1993","21-01-2016","Trainee"),
    Employee(4,"Kunal Kapoor","23-10-1986","21-01-2016","Trainee"),
    Employee(5,"Akash Sethi","23-03-1992","21-01-2016","Trainee")
  )

  def validateDate(date:String):Int={
    try {
      format.parseMillis(date)
      1
    }
    catch {
      case e: IllegalArgumentException => 0
    }

  }

    override def addEmployee(id:Int,name:String,dateOfBirth:String,dateOfJoining:String,designation:String):Int={
    val check=employeeList.filter(a => a.id==id)
    if(check==Nil){
      val validBirth = validateDate(dateOfBirth)
      val validJoining = validateDate(dateOfJoining)
      if(validBirth==1 && validJoining==1) {
        employeeList += Employee(id, name, dateOfBirth, dateOfJoining, designation)
        1
      }
      else if(validBirth==0 && validJoining==0)
        { 4 }
      else if(validBirth==0)
        { 2 }
      else
        { 3 }
    }
    else 0
  }

  override def showAll():List[Employee]={
    employeeList.toList
  }

  override def getEmployee(id:Int):List[Employee]={
  val employee=employeeList.filter(a=>a.id==id)
    employee.toList
  }

  override def updateEmployee(id:Int,name:String,dateOfBirth:String,dateOfJoining:String,designation:String):Int={

    val employee=employeeList.filter(a=>a.id==id)
    val validBirth = validateDate(dateOfBirth)
    val validJoining = validateDate(dateOfJoining)
    if(employee==Nil){ 0 }
    else if(validBirth==1 && validJoining==1) {
      employeeList --= employee
      employeeList += Employee(id, name, dateOfBirth, dateOfJoining, designation)
      1
    }
    else if(validBirth==0 && validJoining==0)
    { 4 }
    else if(validBirth==0)
    { 2 }
    else
    { 3 }
  }

  override def searchName(name:String):List[Employee]={
    val employee=employeeList.filter(a=>a.name==name)
    employee.toList
  }

  override def deleteEmployee(id:Int):Int={
    val employee=employeeList.filter(a=>a.id==id)
    if(employee==Nil){0}
    else {
      employeeList --= employee
      1
    }
  }
}
