export class Benifit {
    id!: number;
    details!: string;
    matricule!: string;
    typeValidation!: {
        id: number,
        type: string,
        matricule: boolean
    }
    employee!:{
      id: number,
      firstName: string,
      lastName: string,
      email: string
    } ;
  }