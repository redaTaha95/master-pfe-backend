import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BenifitPayload } from '../benifit-payload';
import { BenifitService } from 'src/app/services/benifit/benifit.service';
import { TypeValidation } from 'src/app/services/benifit/TypeValidation';

@Component({
  selector: 'app-create-benifits',
  templateUrl: './create-benifits.component.html',
  styleUrls: ['./create-benifits.component.css']
})
export class CreateBenifitsComponent {
  createBenifitForm: FormGroup = new FormGroup({});
  benifitPayload: BenifitPayload;
  isNotValid: boolean = false;
  isError: boolean = false;
  date: Date = new Date("2023-01-01");    
  employeeId!: number;
  typeValidations: Array<TypeValidation> = [];
  showMatriculeField: boolean = false;
  
  constructor(private router: Router,private route: ActivatedRoute, private benifitService: BenifitService) {
    this.benifitPayload = {
      details: null,
      matricule: null,
      employeeId: null,
      typeValidationId: null,
    }
  }

  ngOnInit() {
    this.employeeId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadTypeValidations();
    this.createBenifitForm = new FormGroup({
      details: new FormControl('', Validators.required),
      matricule: new FormControl('', Validators.required),
      employeeId: new FormControl(this.employeeId, Validators.required),
      typeValidationId: new FormControl('', Validators.required),
    });
  }
  createBenifit() {
    this.benifitPayload.details = this.createBenifitForm.get('details')?.value;
    this.benifitPayload.matricule = this.createBenifitForm.get('matricule')?.value;
    this.benifitPayload.employeeId = this.employeeId;
    this.benifitPayload.typeValidationId = this.createBenifitForm.get('typeValidationId')?.value;

    if (this.benifitPayload.details == null || this.benifitPayload.employeeId == null || this.benifitPayload.typeValidationId == null ) {
      this.isNotValid = true;
    } else {
      this.benifitService.createBenifit(this.benifitPayload).subscribe({
        next: () => {
          this.router.navigateByUrl('/benifits');
        },
        error: () => {
          this.isError = true;
        }
      })
    }
  }

  checkMatriculeVisibility() {
    const selectedTypeValidationId = this.createBenifitForm.get('typeValidationId')?.value;
    this.benifitService.getTypeValidationById(selectedTypeValidationId).subscribe(
      (selectedTypeValidation) => {
        this.showMatriculeField = selectedTypeValidation?.matricule === true;
      },
      (error) => {
        console.log("Error getting typeValidation")
      }
    );
  }

  loadTypeValidations() {
    this.benifitService.getAllTypeValidations().subscribe(typeValidations => {
      this.typeValidations = typeValidations;
      console.log(this.typeValidations);
    },
    error => {
      console.log("ERROR getAllTypeValidations");
    });
}
}
