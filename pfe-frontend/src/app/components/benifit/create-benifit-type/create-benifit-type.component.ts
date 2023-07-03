import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BenifitService } from 'src/app/services/benifit/benifit.service';
import { TypeValidationPayload } from '../typeValidationPayload';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-create-benifit-type',
  templateUrl: './create-benifit-type.component.html',
  styleUrls: ['./create-benifit-type.component.css']
})
export class CreateBenifitTypeComponent {
  createTypeValidationForm: FormGroup = new FormGroup({});
  typeValidationPayload: TypeValidationPayload;
  isNotValid: boolean = false;
  isError: boolean = false;

  constructor(private router: Router,private route: ActivatedRoute, private benifitService: BenifitService) {
    this.typeValidationPayload = {
      type: null,
      matricule: null,
    }
  }

  ngOnInit() {
    this.createTypeValidationForm = new FormGroup({
      type: new FormControl('', Validators.required),
      matricule: new FormControl('', Validators.required)
    });
  }

  createTypeValidation() {
    this.typeValidationPayload.type = this.createTypeValidationForm.get('type')?.value;
    this.typeValidationPayload.matricule = this.createTypeValidationForm.get('matricule')?.value;
 
    if (this.typeValidationPayload.type == null || this.typeValidationPayload.matricule == null) {
      this.isNotValid = true;
    } else {
      this.benifitService.createTypeValidation(this.typeValidationPayload).subscribe({
        next: () => {
          this.router.navigateByUrl('/benifits');
        },
        error: () => {
          this.isError = true;
        }
      })
    }
  }
}
