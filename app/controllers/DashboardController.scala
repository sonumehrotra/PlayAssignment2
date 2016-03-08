package controllers

import play.api.data.Form
import play.api.mvc.{Action, Controller}
import play.api.data.Forms._
import play.api.i18n.Messages.Implicits._
import play.api.Play.current
import com.google.inject.Inject
import model.{Dashboard, Employee, EmployeeModel}

class DashboardController  @Inject()(employee: EmployeeModel) extends Controller{

  val dashboardSearchForm:Form[Dashboard]=Form(
    mapping(
      "name"->nonEmptyText
    )(Dashboard.apply)(Dashboard.unapply)
  )

  val employeeForm:Form[Employee]=Form(
    mapping(
      "id"->number(min=1),
      "name"->nonEmptyText,
      "dateOfBirth"->nonEmptyText,
      "dateOfJoining"->nonEmptyText,
      "designation"->nonEmptyText
    )(Employee.apply)(Employee.unapply)
  )

  def showDashboard = Action { implicit request =>
    val employeeList = employee.showAll()
    Ok(views.html.dashboard(dashboardSearchForm, employeeList))
  }


  def searchDashboard = Action{
    implicit request =>
      dashboardSearchForm.bindFromRequest().fold(
        errors=>{Redirect("/showDashboard").flashing("error" -> "No Employee with this name exists.")},
        data => {
          val nameList=employee.searchName(data.name)
          if(nameList==Nil){
            Redirect("/showDashboard").flashing("error" -> "No Employee with this name exists.")
          }
          else{
            Ok(views.html.dashboard(dashboardSearchForm,nameList))
          }
        }
      )
  }

  def showEditEmployeeForm(id:Int) = Action {
    implicit request =>
      val emp=employee.getEmployee(id)
      Ok(views.html.editemployee(employeeForm,emp))
  }

  def processEditEmployeeForm = Action{
    implicit request =>
      employeeForm.bindFromRequest().fold(
        errors => Redirect("/showDashboard").flashing("error" -> "Incorrect Details. Employee Not Added"),
        data => {
          val res: Int = employee.updateEmployee(data.id, data.name, data.dateOfBirth, data.dateOfJoining, data.designation)

          res match{
            case 0 => Redirect("/showDashboard").flashing("error"->"Employee Not Updated.")
            case 1 => Redirect("/showDashboard").flashing("success"->"Employee Updated.")
            case 2 => Redirect("/showDashboard").flashing("error"->"Employee Updated. Date Of Birth is wrong.")
            case 3 => Redirect("/showDashboard").flashing("error"->"Employee Updated. Date Of Joining is wrong.")
            case 4 => Redirect("/showDashboard").flashing("error"->"Employee Updated. Both dates are wrong.")

          }
        }
      )

  }

  def deleteEmployee(id:Int)=Action{
    implicit request =>
      val result= employee.deleteEmployee(id)
      result match {
        case 1 => Redirect("/showDashboard").flashing("success" -> "Employee Deleted")
        case _ => Redirect("/showDashboard").flashing("error" -> "Employee Not Deleted")
      }
  }

  def showAddEmployeeForm = Action {
    implicit request =>
      Ok(views.html.addemployee(employeeForm))
  }

  def processAddEmployeeForm = Action{
    implicit request =>
      employeeForm.bindFromRequest().fold(
        errors =>{ Redirect("/showDashboard").flashing("error"->"Employee Not Added. Wrong Id.")},
        data => {
          val res:Int = employee.addEmployee(data.id,data.name,data.dateOfBirth,data.dateOfJoining,data.designation)
          res match{
            case 0 => Redirect("/showDashboard").flashing("error"->"Employee Not Added. Already Exists.")
            case 1 => Redirect("/showDashboard").flashing("success"->"Employee Added")
            case 2 => Redirect("/showDashboard").flashing("error"->"Employee Not Added. Date Of Birth is wrong.")
            case 3 => Redirect("/showDashboard").flashing("error"->"Employee Not Added. Date Of Joining is wrong.")
            case 4 => Redirect("/showDashboard").flashing("error"->"Employee Not Added. Both dates are wrong.")

          }
        }
      )
  }
}



