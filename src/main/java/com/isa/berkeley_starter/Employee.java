package com.isa.berkeley_starter;

import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.bind.tuple.TupleInput;
import com.sleepycat.bind.tuple.TupleOutput;
import com.sleepycat.je.DatabaseEntry;

public class Employee {

  public String getDepartment() {
    return Department;
  }

  public void setDepartment(String Department) {
    this.Department = Department;
  }

  public String getEmail() {
    return Email;
  }

  public void setEmail(String Email) {
    this.Email = Email;
  }

  public String getName() {
    return Name;
  }

  public void setName(String Name) {
    this.Name = Name;
  }

  public Employee(String Name, String Email, String Department) {
    this.Name = Name;
    this.Email = Email;
    this.Department = Department;
  }

  public Employee() {
  }

  // set name, email, dept
  public void setEmployee(String Name, String Email, String Department) {
    this.Name = Name;
    this.Email = Email;
    this.Department = Department;
  }

  // display name, email, dept information
  public void Display() {

    System.out.println("Name...: " + Name);
    System.out.println("Email..: " + Email);
    System.out.println("Dept...: " + Department);
  }

  // convert object to entry
  public DatabaseEntry objectToEntry() {

    TupleOutput output = new TupleOutput();

    DatabaseEntry entry = new DatabaseEntry();

    // write name, email and department to tuple
    output.writeString(getName());
    output.writeString(getEmail());
    output.writeString(getDepartment());

    TupleBinding.outputToEntry(output, entry);

    return entry;
  }

  // convert entry to object
  public void entryToObject(DatabaseEntry entry) {

    TupleInput input = TupleBinding.entryToInput(entry);

    // set name, email and department
    setName(input.readString());
    setEmail(input.readString());
    setDepartment(input.readString());
  }

  private String Name;
  private String Email;
  private String Department;

}