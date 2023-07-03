import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BenifitService } from 'src/app/services/benifit/benifit.service';
import { BenifitPayload } from '../benifit-payload';
import { TypeValidation } from 'src/app/services/benifit/TypeValidation';
import { Benifit } from 'src/app/services/benifit/Benifit';

@Component({
  selector: 'app-edit-benifits',
  templateUrl: './edit-benifits.component.html',
  styleUrls: ['./edit-benifits.component.css']
})
export class EditBenifitsComponent {
  editBenifitForm: FormGroup = new FormGroup({});
  isNotValid: boolean = false;
  isError: boolean = false;
  benifitId!: number;
  employeeId!: number;
  benifitPayload: BenifitPayload;
  typeValidations: Array<TypeValidation> = [];
  benifit: Benifit = new Benifit();
  showMatriculeField: boolean = true;

  constructor(private route: ActivatedRoute, private router: Router, private benifitService: BenifitService) {
    this.benifitPayload = {
      details: null,
      matricule: null,
      employeeId: null,
      typeValidationId: null,
    };
    this.loadTypeValidations();
   
    this.editBenifitForm = new FormGroup({
      details: new FormControl('', Validators.required),
      matricule: new FormControl('', Validators.required),
      employeeId: new FormControl('', Validators.required),
      typeValidationId: new FormControl('', Validators.required),
    });
  
  }

  ngOnInit() {
    this.benifitId = Number(this.route.snapshot.paramMap.get('id'));
 
    this.benifitService.getBenifitById(this.benifitId).subscribe({
      next: (response) => {
    
        this.editBenifitForm.setValue({
          
          details: response.details,
          matricule: response.matricule,
          employeeId: response.employee.id,
          typeValidationId: response.typeValidation.id 
      
        });
      },
      error: () => {
        this.router.navigate(['/404']);
      }
    });
  }

  checkMatriculeVisibility() {
    const selectedTypeValidationId = this.editBenifitForm.get('typeValidationId')?.value;
    this.benifitService.getTypeValidationById(selectedTypeValidationId).subscribe(
      (selectedTypeValidation) => {
        this.showMatriculeField = selectedTypeValidation?.matricule === true;
      },
      (error) => {
        console.log("Error getting typeValidation")
      }
    );
  }

  editbenifit() {
    if (this.editBenifitForm.invalid) {
      this.isNotValid = true;
      return;
    }

    const benifitPayload: BenifitPayload = {
      details: this.editBenifitForm.value.details,
      matricule: this.editBenifitForm.value.matricule,
      employeeId: this.editBenifitForm.value.employeeId,
      typeValidationId: this.editBenifitForm.value.typeValidationId,
    };
    this.benifitService.editBenifit(benifitPayload, this.benifitId).subscribe({
      next: () => {
        this.router.navigateByUrl('/benifits');
      },
      error: () => {
        this.isError = true;
      }
    });
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
