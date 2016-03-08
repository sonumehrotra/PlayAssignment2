//package controllers
//
//import model.{Dashboard, Employee, EmployeeModel}
//import play.api.data.Form
//import play.api.mvc.{Action, Controller}
//import play.api.data.Forms._
//import com.google.inject.Inject
//import scala.concurrent.Future
//import play.api.libs.concurrent.Execution.Implicits._
//import play.api.i18n.Messages.Implicits._
//import play.api.Play.current
//
//
//class EditEmployeeController @Inject()(employee: EmployeeModel) extends Controller{
//
//  val editEmployeeForm:Form[Employee]=Form(
//    mapping(
//      "id"->number(min=1,max=3),
//      "name"->nonEmptyText,
//      "dateOfBirth"->nonEmptyText,
//      "dateOfJoining"->nonEmptyText,
//      "designation"->nonEmptyText
//    )(Employee.apply)(Employee.unapply)
//  )
//
//  val dashboardSearchForm:Form[Dashboard]=Form(
//    mapping(
//      "name"->nonEmptyText
//    )(Dashboard.apply)(Dashboard.unapply)
//  )
//
//  def showEditEmployeeForm(id:Int) = Action {
//    implicit request =>
//      val emp=employee.getEmployee(id)
//      Ok(views.html.editemployee(editEmployeeForm,emp))
//  }
//
//  def processEditEmployeeForm = Action{
//    implicit request =>
//      editEmployeeForm.bindFromRequest().fold(
//        errors => Ok("errors"),
//        data => {
//          val res:Int = employee.updateEmployee(data.id,data.name,data.dateOfBirth,data.dateOfJoining,data.designation)
//          if(res==1)
//            Ok(views.html.dashboard(dashboardSearchForm,employee.showAll()))
//          else
//            Ok("with errors")
//        }
//      )
//  }
//
//  def deleteEmployee(id:Int)=Action{
//    implicit request =>
//      val result= employee.deleteEmployee(id)
//      if(result==1)
//        Ok(views.html.dashboard(dashboardSearchForm,employee.showAll()))
//      else
//        Ok("no deletion")
//  }
//}
