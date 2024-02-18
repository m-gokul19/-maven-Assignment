package com.controller;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Employee.Employee;

@Controller
public class EmpController {

	@RequestMapping("/home")
	public String home() {

		return "home";
	}

	@RequestMapping("/showrecords")
	public String showrecords(Model data) {

		SessionFactory factory = new Configuration().configure().buildSessionFactory();

		Session session = factory.openSession();

		Query q2 = session.createQuery("from Employee");

		List<Employee> empList = q2.getResultList();

		data.addAttribute("empList", empList);

		return "show-record";
	}

	@RequestMapping("/AddRecordForm")
	public String addrecord() {

		return "addRecord";
	}

	// add-data
	@PostMapping("/LinkRecord")
	public String linkrecord(@RequestParam String employeeName, @RequestParam String employeeAddress,
			@RequestParam int employeePhone, @RequestParam int employeeSalary, Model data) {

		SessionFactory factory = new Configuration().configure().buildSessionFactory();

		Session session = factory.openSession();
		try {
			Transaction trans = session.beginTransaction();

			Employee emp = new Employee(employeeName, employeeAddress, employeePhone, employeeSalary);

			session.save(emp);
			trans.commit();
		}

		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		Query q1 = session.createQuery("from Employee");

		List<Employee> empList = q1.getResultList();

		data.addAttribute("empList", empList);

		return "show-record";
	}

	// update
	@GetMapping("/updateEmployee")
	public String update(@RequestParam int id, Model data) {

		SessionFactory factory = new Configuration().configure().buildSessionFactory();

		Session session = factory.openSession();
		try {
			Employee emp = session.get(Employee.class, id);

			data.addAttribute("empList", emp);
		}

		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return "updateEmployee";
	}

	// delete

	@GetMapping("/DeleteEmployee")
	public String delete(@RequestParam int id, Model data) {
		SessionFactory factory = new Configuration().configure().buildSessionFactory();

		Session session = factory.openSession();

		try {
			Transaction trans = session.beginTransaction();

			Employee emp = new Employee(id, null, null, 0, 0);

			session.delete(emp);
			trans.commit();

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		Query q2 = session.createQuery("from Employee");

		List<Employee> empList = q2.getResultList();

		data.addAttribute("empList", empList);

		return "show-record";
	}

	// afterupdate-return to show-record
	@GetMapping("/updated")

	public String afterupdate(@RequestParam int id, @RequestParam String employeeName,
			@RequestParam String employeeAddress, @RequestParam int employeePhone, @RequestParam int employeeSalary,
			Model data) {

		SessionFactory factory = new Configuration().configure().buildSessionFactory();

		Session session = factory.openSession();
		try {
			Transaction trans = session.beginTransaction();

			Employee emp = new Employee(id, employeeName, employeeAddress, employeePhone, employeeSalary);

			session.update(emp);
			Query q3 = session.createQuery("from Employee");

			List<Employee> empList = q3.getResultList();

			data.addAttribute("empList", empList);
			trans.commit();
		}

		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return "show-record";
	}

}
