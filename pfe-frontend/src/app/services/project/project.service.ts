import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.development';
import { Project } from './Project';
import { ProjectPayload } from 'src/app/components/project/project-payload';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {
  baseUrl = environment.baseURL;

  constructor(private http: HttpClient) { }

  getAllProjects(): Observable<Array<Project>> {
    return this.http.get<Array<Project>>(this.baseUrl + '/projects');
  }

  createProject(ProjectPayload: ProjectPayload): Observable<Project> {
    return this.http.post<Project>(this.baseUrl + '/projects', ProjectPayload);
  }

  getProjectById(id: number): Observable<Project> {
    return this.http.get<Project>(this.baseUrl + '/projects/' + id);
  }

  editProject(ProjectPayload: ProjectPayload, ProjectId: number): Observable<Project> {
    return this.http.put<Project>(this.baseUrl + '/projects/' + ProjectId, ProjectPayload);
  }

  deleteProject(ProjectId: number): Observable<void> {
    return this.http.delete<void>(this.baseUrl + '/projects/' + ProjectId);
  }  
}
