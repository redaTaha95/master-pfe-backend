import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.development';
import { Task } from './Task';
import { TaskPayload } from 'src/app/components/task/task-payload';

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  baseUrl = environment.baseURL;

  constructor(private http: HttpClient) { }

  getAllTasks(): Observable<Array<Task>> {
    return this.http.get<Array<Task>>(this.baseUrl + '/tasks');
  }

  createTask(TaskPayload: TaskPayload): Observable<Task> {
    return this.http.post<Task>(this.baseUrl + '/tasks', TaskPayload);
  }

  getTaskById(id: number): Observable<Task> {
    return this.http.get<Task>(this.baseUrl + '/tasks/' + id);
  }

  getTaskByProjectId(id: number): Observable<Array<Task>> {
    return this.http.get<Array<Task>>(this.baseUrl + '/tasks/project/' + id);
  }

  editTask(taskId: number, taskPayload: TaskPayload): Observable<Task> {
    return this.http.put<Task>(this.baseUrl + '/tasks/' + taskId, taskPayload);
  }

  deleteTask(TaskId: number): Observable<void> {
    return this.http.delete<void>(this.baseUrl + '/tasks/' + TaskId);
  }  
}
