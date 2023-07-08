export class Payroll {
  id!: number;
  payrollDate!: Date;
  monthlyNetSalary!: number;
  monthlyBasedSalary!: number;
  monthlyHoursWorked!: number;
  bonusPaiment!: number;
  employee!:{
    id: number,
    firstName: string,
    lastName: string,
    email: string
  } ;
}