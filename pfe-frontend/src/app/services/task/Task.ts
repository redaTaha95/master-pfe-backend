export class Task {
    id!: number;
    name!: string;
    description!: string;
    projectId!: number;
    status!: string;
    employee!:{
      id: number,
      firstName: string,
      lastName: string,
      email: string
    } ;
    startDate!: Date;
    endDate!: Date;
  }