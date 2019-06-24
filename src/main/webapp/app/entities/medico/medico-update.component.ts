import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IMedico, Medico } from 'app/shared/model/medico.model';
import { MedicoService } from './medico.service';

@Component({
  selector: 'jhi-medico-update',
  templateUrl: './medico-update.component.html'
})
export class MedicoUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nome: [],
    usuario: [],
    email: [],
    matricula: [],
    senha: []
  });

  constructor(protected medicoService: MedicoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ medico }) => {
      this.updateForm(medico);
    });
  }

  updateForm(medico: IMedico) {
    this.editForm.patchValue({
      id: medico.id,
      nome: medico.nome,
      usuario: medico.usuario,
      email: medico.email,
      matricula: medico.matricula,
      senha: medico.senha
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const medico = this.createFromForm();
    if (medico.id !== undefined) {
      this.subscribeToSaveResponse(this.medicoService.update(medico));
    } else {
      this.subscribeToSaveResponse(this.medicoService.create(medico));
    }
  }

  private createFromForm(): IMedico {
    return {
      ...new Medico(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      usuario: this.editForm.get(['usuario']).value,
      email: this.editForm.get(['email']).value,
      matricula: this.editForm.get(['matricula']).value,
      senha: this.editForm.get(['senha']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMedico>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
