import controllers.DashboardController
import model.{Employee, EmployeeModel}
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.mock.Mockito
import org.specs2.runner.JUnitRunner
import play.api.test.{FakeRequest, WithApplication}
import play.api.test._
import play.api.test.Helpers._
import org.mockito.Mockito._


/**
  * Created by knoldus on 8/3/16.
  */

@RunWith(classOf[JUnitRunner])
class DashboardControllerSpec extends Specification with Mockito{

  val service= mock[EmployeeModel]
  val controller = new DashboardController(service)

  "Dashboard application" should {

    "show the dashboard page" in new WithApplication {
      when(service.showAll()).thenReturn(List(Employee(8, "Rishabh Verma", "23-02-1988", "21-01-2016", "Trainee")))
      val result = call(controller.showDashboard, FakeRequest(GET, "/showDashboard"))
      contentType(result) must beSome.which(_ == "text/html")
    }


    "Show the DashBoard Page without Mockito" in new WithApplication {
      val result = route(FakeRequest(GET, "/showDashboard")).get
      status(result) must equalTo(OK)
      contentType(result) must beSome.which(_ == "text/html")
    }


      "Show the add page" in new WithApplication {
        val result = route(FakeRequest(GET, "/showAddEmployeeForm")).get
        status(result) must equalTo(OK)
        contentType(result) must beSome.which(_ == "text/html")
      }

      "Search employee by name" in new WithApplication {
        val result = route(FakeRequest(POST, "/searchDashboard").withFormUrlEncodedBody("name" -> "Sonu Mehrotra"))
        status(result.get) must equalTo(OK)
        contentType(result.get) must beSome.which(_ == "text/html")
      }

      "Show the edit page" in new WithApplication {
        val result = route(FakeRequest(GET, "/showEditEmployeeForm/1")).get
        status(result) must equalTo(OK)
        contentType(result) must beSome.which(_ == "text/html")
      }

      "Add an employee" in new WithApplication{
        val result = route(FakeRequest(POST, "/processAddEmployeeForm").withFormUrlEncodedBody(
          "id"->"8","name" -> "Akanksha","dateOfBirth"->"08-01-1992",
          "dateOfJoining"->"01-02-2016","designation"->"trainer"))
        status(result.get) must equalTo(SEE_OTHER)
      }

      "Add wrong employee with wrong id" in new WithApplication{
        val result = route(FakeRequest(POST, "/processAddEmployeeForm").withFormUrlEncodedBody(
          "id"->"abc","name" -> "Akanksha","dateOfBirth"->"08-01-1992",
          "dateOfJoining"->"01-02-2016","designation"->"trainer"))
        status(result.get) must equalTo(SEE_OTHER)

      }

    "Add wrong employee with wrong date of joining" in new WithApplication{
      val result = route(FakeRequest(POST, "/processAddEmployeeForm").withFormUrlEncodedBody(
        "id"->"9","name" -> "Akanksha","dateOfBirth"->"08-01-1992",
        "dateOfJoining"->"abc","designation"->"trainer"))
      status(result.get) must equalTo(SEE_OTHER)

    }

    "Add wrong employee with wrong date of birth" in new WithApplication{
      val result = route(FakeRequest(POST, "/processAddEmployeeForm").withFormUrlEncodedBody(
        "id"->"9","name" -> "Akanksha","dateOfBirth"->"abc",
        "dateOfJoining"->"01-02-2016","designation"->"trainer"))
      status(result.get) must equalTo(SEE_OTHER)

    }

    "Add wrong employee with both wrong dates" in new WithApplication{
      val result = route(FakeRequest(POST, "/processAddEmployeeForm").withFormUrlEncodedBody(
        "id"->"9","name" -> "Akanksha","dateOfBirth"->"abc",
        "dateOfJoining"->"cde","designation"->"trainer"))
      status(result.get) must equalTo(SEE_OTHER)

    }

    "Edit an employee correctly" in new WithApplication{
      val result = route(FakeRequest(POST, "/processAddEmployeeForm").withFormUrlEncodedBody(
        "id"->"1","name" -> "Sonu","dateOfBirth"->"25-10-1992",
        "dateOfJoining"->"21-01-2016","designation"->"trainer"))
      status(result.get) must equalTo(SEE_OTHER)
    }

    "Edit an employee with wrong date of birth" in new WithApplication{
      val result = route(FakeRequest(POST, "/processAddEmployeeForm").withFormUrlEncodedBody(
        "id"->"1","name" -> "Sonu","dateOfBirth"->"abc",
        "dateOfJoining"->"21-01-2016","designation"->"trainer"))
      status(result.get) must equalTo(SEE_OTHER)
    }

    "Edit an employee with wrong date of joining" in new WithApplication{
      val result = route(FakeRequest(POST, "/processAddEmployeeForm").withFormUrlEncodedBody(
        "id"->"1","name" -> "Sonu","dateOfBirth"->"25-10-1992",
        "dateOfJoining"->"abc","designation"->"trainer"))
      status(result.get) must equalTo(SEE_OTHER)
    }

    "Edit an employee with worong dates" in new WithApplication{
      val result = route(FakeRequest(POST, "/processAddEmployeeForm").withFormUrlEncodedBody(
        "id"->"1","name" -> "Sonu","dateOfBirth"->"xyz",
        "dateOfJoining"->"abc","designation"->"trainer"))
      status(result.get) must equalTo(SEE_OTHER)
    }

    "Delete an employee" in new WithApplication{
      val result=route(FakeRequest(GET,"/deleteEmployee/1"))
      status(result.get) must equalTo(SEE_OTHER)
    }

    "search an employee that does not exist" in new WithApplication{
      val result=route(FakeRequest(POST, "/searchDashboard").withFormUrlEncodedBody("name" -> "abc"))
      status(result.get) must equalTo(SEE_OTHER)
    }

    "search an employee that does not exist" in new WithApplication{
      val result=route(FakeRequest(POST, "/searchDashboard").withFormUrlEncodedBody("name" -> "Sonu Mehrotra"))
      status(result.get) must equalTo(OK)
    }

    }
  }

