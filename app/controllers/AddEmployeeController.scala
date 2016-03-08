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
//class AddEmployeeController @Inject()(employee: EmployeeModel) extends Controller{
//
//  val addEmployeeForm:Form[Employee]=Form(
//    mapping(
//      "id"->number(min=1),
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
//  def showAddEmployeeForm = Action {
//    implicit request =>
//      Ok(views.html.addemployee(addEmployeeForm))
//  }
//
//  def processAddEmployeeForm = Action{
//    implicit request =>
//      addEmployeeForm.bindFromRequest().fold(
//        errors => Ok("errors"),
//        data => {
//          val res:Int = employee.addEmployee(data.id,data.name,data.dateOfBirth,data.dateOfJoining,data.designation)
//          if(res==1)
//            Ok(views.html.dashboard(dashboardSearchForm,employee.showAll()))
//          else
//            Ok("with errors")
//        }
//      )
//  }
//}
