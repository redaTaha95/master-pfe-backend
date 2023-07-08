import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Project } from 'src/app/services/project/Project';
import { ProjectService } from 'src/app/services/project/project.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-index-project',
  templateUrl: './index-project.component.html',
  styleUrls: ['./index-project.component.css']
})
export class IndexProjectComponent {
  projects: Array<Project> = [];

  constructor(private router: Router, private projectService: ProjectService) {
    this.projectService.getAllProjects().subscribe(projects => {
      this.projects = projects;
    })
  }

  ngOnInit(): void {
  }

  deleteProject(projectId: number): void {
    Swal.fire({
      title: 'Confirmation',
      text: 'Voulez-vous vraiment supprimer ce project?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Supprimer',
      cancelButtonText: 'Annuler'
    }).then((result) => {
      if (result.isConfirmed) {
        this.projectService.deleteProject(projectId).subscribe({
          next: () => {
            // Success message or further actions
            Swal.fire('Supprimé!', 'Le project a été supprimé.', 'success');

            this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
              this.router.navigate(['/projects']);
            });
          },
          error: () => {
            // Error message or handling
            Swal.fire('Erreur!', 'Une erreur s\'est produite lors de la suppression de le project.', 'error');
          }
        });
      }
    });
  }
}
