import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRemedio, Remedio } from 'app/shared/model/remedio.model';
import { RemedioService } from './remedio.service';
import { IDoenca } from 'app/shared/model/doenca.model';
import { DoencaService } from 'app/entities/doenca';
import { IEstoque } from 'app/shared/model/estoque.model';
import { EstoqueService } from 'app/entities/estoque';
import { IReserva } from 'app/shared/model/reserva.model';
import { ReservaService } from 'app/entities/reserva';

@Component({
  selector: 'jhi-remedio-update',
  templateUrl: './remedio-update.component.html'
})
export class RemedioUpdateComponent implements OnInit {
  isSaving: boolean;

  doencas: IDoenca[];

  estoques: IEstoque[];

  reservas: IReserva[];

  editForm = this.fb.group({
    id: [],
    nome: [],
    laboratorio: [],
    estoque: [],
    reserva: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected remedioService: RemedioService,
    protected doencaService: DoencaService,
    protected estoqueService: EstoqueService,
    protected reservaService: ReservaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ remedio }) => {
      this.updateForm(remedio);
    });
    this.doencaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDoenca[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDoenca[]>) => response.body)
      )
      .subscribe((res: IDoenca[]) => (this.doencas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.estoqueService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEstoque[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEstoque[]>) => response.body)
      )
      .subscribe((res: IEstoque[]) => (this.estoques = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.reservaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IReserva[]>) => mayBeOk.ok),
        map((response: HttpResponse<IReserva[]>) => response.body)
      )
      .subscribe((res: IReserva[]) => (this.reservas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(remedio: IRemedio) {
    this.editForm.patchValue({
      id: remedio.id,
      nome: remedio.nome,
      laboratorio: remedio.laboratorio,
      estoque: remedio.estoque,
      reserva: remedio.reserva
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const remedio = this.createFromForm();
    if (remedio.id !== undefined) {
      this.subscribeToSaveResponse(this.remedioService.update(remedio));
    } else {
      this.subscribeToSaveResponse(this.remedioService.create(remedio));
    }
  }

  private createFromForm(): IRemedio {
    return {
      ...new Remedio(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      laboratorio: this.editForm.get(['laboratorio']).value,
      estoque: this.editForm.get(['estoque']).value,
      reserva: this.editForm.get(['reserva']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRemedio>>) {
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

  trackDoencaById(index: number, item: IDoenca) {
    return item.id;
  }

  trackEstoqueById(index: number, item: IEstoque) {
    return item.id;
  }

  trackReservaById(index: number, item: IReserva) {
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
