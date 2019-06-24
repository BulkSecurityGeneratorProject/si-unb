import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IFarmacia, Farmacia } from 'app/shared/model/farmacia.model';
import { FarmaciaService } from './farmacia.service';
import { IEstoque } from 'app/shared/model/estoque.model';
import { EstoqueService } from 'app/entities/estoque';
import { IReserva } from 'app/shared/model/reserva.model';
import { ReservaService } from 'app/entities/reserva';

@Component({
  selector: 'jhi-farmacia-update',
  templateUrl: './farmacia-update.component.html'
})
export class FarmaciaUpdateComponent implements OnInit {
  isSaving: boolean;

  estoques: IEstoque[];

  reservas: IReserva[];

  editForm = this.fb.group({
    id: [],
    nome: [],
    cidade: [],
    endereco: [],
    longitude: [],
    latitude: [],
    estoque: [],
    reserva: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected farmaciaService: FarmaciaService,
    protected estoqueService: EstoqueService,
    protected reservaService: ReservaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ farmacia }) => {
      this.updateForm(farmacia);
    });
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

  updateForm(farmacia: IFarmacia) {
    this.editForm.patchValue({
      id: farmacia.id,
      nome: farmacia.nome,
      cidade: farmacia.cidade,
      endereco: farmacia.endereco,
      longitude: farmacia.longitude,
      latitude: farmacia.latitude,
      estoque: farmacia.estoque,
      reserva: farmacia.reserva
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const farmacia = this.createFromForm();
    if (farmacia.id !== undefined) {
      this.subscribeToSaveResponse(this.farmaciaService.update(farmacia));
    } else {
      this.subscribeToSaveResponse(this.farmaciaService.create(farmacia));
    }
  }

  private createFromForm(): IFarmacia {
    return {
      ...new Farmacia(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      cidade: this.editForm.get(['cidade']).value,
      endereco: this.editForm.get(['endereco']).value,
      longitude: this.editForm.get(['longitude']).value,
      latitude: this.editForm.get(['latitude']).value,
      estoque: this.editForm.get(['estoque']).value,
      reserva: this.editForm.get(['reserva']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFarmacia>>) {
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

  trackEstoqueById(index: number, item: IEstoque) {
    return item.id;
  }

  trackReservaById(index: number, item: IReserva) {
    return item.id;
  }
}
