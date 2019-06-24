import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IEstoque, Estoque } from 'app/shared/model/estoque.model';
import { EstoqueService } from './estoque.service';

@Component({
  selector: 'jhi-estoque-update',
  templateUrl: './estoque-update.component.html'
})
export class EstoqueUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    quantidade: []
  });

  constructor(protected estoqueService: EstoqueService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ estoque }) => {
      this.updateForm(estoque);
    });
  }

  updateForm(estoque: IEstoque) {
    this.editForm.patchValue({
      id: estoque.id,
      quantidade: estoque.quantidade
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const estoque = this.createFromForm();
    if (estoque.id !== undefined) {
      this.subscribeToSaveResponse(this.estoqueService.update(estoque));
    } else {
      this.subscribeToSaveResponse(this.estoqueService.create(estoque));
    }
  }

  private createFromForm(): IEstoque {
    return {
      ...new Estoque(),
      id: this.editForm.get(['id']).value,
      quantidade: this.editForm.get(['quantidade']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstoque>>) {
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
