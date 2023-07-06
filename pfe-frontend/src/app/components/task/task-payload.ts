export class TaskPayload {
    name!: string | null;
    description!: string | null;
    projectId!: number | null;
    status!: string;
    employeeId!:number | null;
    startDate!: Date | null;
    endDate!: Date | null;
}