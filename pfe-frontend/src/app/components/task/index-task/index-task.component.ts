import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Task } from 'src/app/services/task/Task';
import { TaskService } from 'src/app/services/task/task.service';
import Swal from 'sweetalert2';
import {
  CdkDragDrop,
  moveItemInArray,
  transferArrayItem,
  CdkDropList
} from '@angular/cdk/drag-drop';
import { TaskPayload } from '../task-payload';
import { EmployeeService } from 'src/app/services/employee/employee.service';


@Component({
  selector: 'app-index-task',
  templateUrl: './index-task.component.html',
  styleUrls: ['./index-task.component.css'],
})


export class IndexTaskComponent {
  tasks: Array<Task> = [];
  projectId!: number;
  todoTasks: Task[] = [];
  inProgressTasks: Task[] = [];
  doneTasks: Task[] = [];
  todo: string = 'TODO';
  inprogress: string = 'IN_PROGRESS';
  done: string = 'DONE';

  constructor(private route: ActivatedRoute,private router: Router, private taskService: TaskService,private employeeService: EmployeeService) {
    this.projectId = Number(this.route.snapshot.paramMap.get('id'));
  }
  
  ngOnInit(): void {
    this.loadData();
  }

loadData(){
  this.taskService.getTaskByProjectId(this.projectId).subscribe(tasks => {
    this.tasks = tasks;
    this.todoTasks = tasks.filter(task => task.status === 'TODO');
    this.inProgressTasks = tasks.filter(task => task.status === 'IN_PROGRESS');
    this.doneTasks = tasks.filter(task => task.status === 'DONE');
    
  })
}

  drop(event: CdkDragDrop<Task[]>) {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      const previousTask: Task = event.previousContainer.data[event.previousIndex];
      const updatedTask = new TaskPayload();
      let status: string;
      const dropListId = event.container.id;
  
      if (dropListId === 'cdk-drop-list-0') {
        status = 'TODO';
      } else if (dropListId === 'cdk-drop-list-1') {
        status = 'IN_PROGRESS';
      } else if (dropListId === 'cdk-drop-list-2') {
        status = 'DONE';
      } else {
        status = 'ERROR';
      }

      updatedTask.name = previousTask.name;
      updatedTask.description = previousTask.description;
      updatedTask.projectId = previousTask.projectId;
      updatedTask.status = status;
      updatedTask.employeeId = previousTask.employee.id;
      updatedTask.startDate = previousTask.startDate;
      updatedTask.endDate = previousTask.endDate;
      console.log(updatedTask);
      this.taskService.editTask(previousTask.id,updatedTask).subscribe((task) => {
        console.log(updatedTask)
      });
  
      transferArrayItem(event.previousContainer.data, event.container.data, event.previousIndex, event.currentIndex);
    }
  }
  
  
  deletetask(taskId: number): void {
    Swal.fire({
      title: 'Confirmation',
      text: 'Voulez-vous vraiment supprimer ce congé payé?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Supprimer',
      cancelButtonText: 'Annuler'
    }).then((result) => {
      if (result.isConfirmed) {
        this.taskService.deleteTask(taskId).subscribe({
          next: () => {
            // Success message or further actions
            Swal.fire('Supprimé!', 'Le congé payé a été supprimé.', 'success');

            this.loadData();
          },
          error: () => {
            // Error message or handling
            Swal.fire('Erreur!', 'Une erreur s\'est produite lors de la suppression de l\'employé.', 'error');
          }
        });
      }
    });
  }
}
