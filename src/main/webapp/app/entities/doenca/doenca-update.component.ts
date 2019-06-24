import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDoenca, Doenca } from 'app/shared/model/doenca.model';
import { DoencaService } from './doenca.service';
import { IRemedio } from 'app/shared/model/remedio.model';
import { RemedioService } from 'app/entities/remedio';

@Component({
  selector: 'jhi-doenca-update',
  templateUrl: './doenca-update.component.html'
})
export class DoencaUpdateComponent implements OnInit {
  isSaving: boolean;

  remedios: IRemedio[];

  editForm = this.fb.group({
    id: [],
    nome: [],
    cid: [],
    estoques: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected doencaService: DoencaService,
    protected remedioService: RemedioService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ doenca }) => {
      this.updateForm(doenca);
    });
    this.remedioService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IRemedio[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRemedio[]>) => response.body)
      )
      .subscribe((res: IRemedio[]) => (this.remedios = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(doenca: IDoenca) {
    this.editForm.patchValue({
      id: doenca.id,
      nome: doenca.nome,
      cid: doenca.cid,
      estoques: doenca.estoques
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const doenca = this.createFromForm();
    if (doenca.id !== undefined) {
      this.subscribeToSaveResponse(this.doencaService.update(doenca));
    } else {
      this.subscribeToSaveResponse(this.doencaService.create(doenca));
    }
  }

  private createFromForm(): IDoenca {
    return {
      ...new Doenca(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      cid: this.editForm.get(['cid']).value,
      estoques: this.editForm.get(['estoques']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDoenca>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackRemedioById(index: number, item: IRemedio) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
