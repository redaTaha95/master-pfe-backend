export class PaidTimeOff {
    id!: number;
    details!: string;
    startDate!: Date;
    endDate!: Date;
    employee!:{
        id: number,
        firstName: string,
        lastName: string,
        email: string
      } ;
  }