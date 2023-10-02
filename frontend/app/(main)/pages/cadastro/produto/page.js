"use client";
import React, { useState, useEffect, useRef } from 'react';
import { classNames } from 'primereact/utils';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Toast } from 'primereact/toast';
import { Button } from 'primereact/button';
import { FileUpload } from 'primereact/fileupload';
import { Rating } from 'primereact/rating';
import { Toolbar } from 'primereact/toolbar';
import { InputTextarea } from 'primereact/inputtextarea';
import { RadioButton } from 'primereact/radiobutton';
import { InputNumber } from 'primereact/inputnumber';
import { Dialog } from 'primereact/dialog';
import { InputText } from 'primereact/inputtext';
import { EstadoService } from './../../../../../demo/service/cadastro/EstadoService';
import ColunaOpcoes from './../../../../../demo/components/ColunaOpcoes';
import { ProdutoService } from '../../../../../demo/service/cadastro/ProdutoService';
import { MarcaService } from '../../../../../demo/service/cadastro/MarcaService';
import { CategoriaService } from '../../../../../demo/service/cadastro/CategoriaService';
import { Dropdown } from 'primereact/dropdown';

const Produto = () => {
    let objetoNovo = {
        descricaoCurta:"",
        descricaoDetalhada:"",
        valorCusto:"",
        valorVenda:"",
        marca: "",
        categoria:""
    };

    const [objetos, setObjetos] = useState(null);
    const[marcas,setMarcas] = useState(null);
    const [categorias,setCategorias] = useState(null);
    const [objetoDialog, setObjetoDialog] = useState(false);
    const [objetoDeleteDialog, setObjetoDeleteDialog] = useState(false);
    const [objeto, setObjeto] = useState(objetoNovo);
    const [submitted, setSubmitted] = useState(false);
    const [globalFilter, setGlobalFilter] = useState(null);
    const toast = useRef(null);
    const dt = useRef(null);
    const objetoService = new ProdutoService();
    const categoriaService = new CategoriaService();
    const marcaService = new MarcaService();

    useEffect(() => {
        if (objetos == null) {
            objetoService.listarTodos().then(res => {
                setObjetos(res.data)

            });

        }
    }, [objetos]);

    useEffect(() => {
        
        marcaService.listarTodos().then(res => {
            setMarcas(res.data);
        });

        categoriaService.listarTodos().then(res => {
            setCategorias(res.data);
        });

    },[]);

    const openNew = () => {
        setObjeto(objetoNovo);
        setSubmitted(false);
        setObjetoDialog(true);
    }

    const hideDialog = () => {
        setSubmitted(false);
        setObjetoDialog(false);
    }

    const hideDeleteObjetoDialog = () => {
        setObjetoDeleteDialog(false);
    }



    const saveObjeto = () => {
        setSubmitted(true);

        if (objeto.descricaoCurta.trim()) {
            let _objeto = { ...objeto };
            if (objeto.id) {
                objetoService.alterar(_objeto).then(data => {
                    toast.current.show({ severity: 'success', summary: 'Sucesso', detail: 'Alterado com Sucesso', life: 3000 });
                    setObjetos(null);
                });
            }
            else {
                objetoService.inserir(_objeto).then(data => {
                    toast.current.show({ severity: 'success', summary: 'Sucesso', detail: 'Inserido com Sucesso', life: 3000 });
                    setObjetos(null);
                });

            }
            setObjetoDialog(false);
            setObjeto(objetoNovo);
        }
    }

    const editObjeto = (objeto) => {
        setObjeto({ ...objeto });
        setObjetoDialog(true);
    }

    const confirmDeleteObjeto = (objeto) => {
        setObjeto(objeto);
        setObjetoDeleteDialog(true);
    }

    const deleteObjeto = () => {
    
        objetoService.excluir(objeto.id).then(data => {
            toast.current.show({ severity: 'success', summary: 'Sucesso', detail: 'Removido', life: 3000 });

            setObjetos(null);
            setObjetoDeleteDialog(false);
         
        });
    }

    const onInputChange = (e, name) => {
        const val = (e.target && e.target.value) || '';
        let _objeto = { ...objeto };
        _objeto[`${name}`] = val;

        setObjeto(_objeto);
    }

    const leftToolbarTemplate = () => {
        return (
            <React.Fragment>
                <div className="my-2">
                    <Button label="Novo Estado" icon="pi pi-plus" className="p-button-success mr-2" onClick={openNew} />

                </div>
            </React.Fragment>
        )
    }

    const idBodyTemplate = (rowData) => {
        return (
            <>
                <span className="p-column-title">ID</span>
                {rowData.id}
            </>
        );
    }
    const selectedMarcaTemplate = (marca) => {
        if(marca){
            return (
                <div className="flex align-items-center">
                    <div>{marca.nome}</div>
                </div>
            );
        }
    }

    const selectedCategoriaTemplate = (categoria) => {
        if(categoria){
            return (
                <div className="flex align-items-center">
                    <div>{categoria.nome}</div>
                </div>
                
            );
            
        }
    }

    const categoriaOptionTemplate = (categoria) => {
        return (
            <div className="flex align-items-center">
                <div>{categoria.nome}</div>
            </div>
        );
    }

    const marcaOptionTemplate = (marca) => {
        return (
            <div className="flex align-items-center">
                <div>{marca.nome}</div>
            </div>
        );
    };

    const descricaoCurtaBodyTemplate = (rowData) => {
        return (
            <>
                <span className="p-column-title">Descrição curta</span>
                {rowData.descricaoCurta}
            </>
        );
    }

    const descricaoDetalhadaBodyTemplate = (rowData) => {
        return (
            <>
                <span className="p-column-title">Descrição detalhada</span>
                {rowData.descricaoDetalhada}
            </>
        );
    }

    const header = (
        <div className="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
            <h5 className="m-0">Estados Cadastrados</h5>
            <span className="block mt-2 md:mt-0 p-input-icon-left">
                <i className="pi pi-search" />
                <InputText type="search" onInput={(e) => setGlobalFilter(e.target.value)} placeholder="Buscar..." />
            </span>
        </div>
    );

    const objetoDialogFooter = (
        <>
            <Button label="Cancelar" icon="pi pi-times" className="p-button-text" onClick={hideDialog} />
            <Button label="Salvar" icon="pi pi-check" className="p-button-text" onClick={saveObjeto} />
        </>
    );

    const deleteObjetoDialogFooter = (
        <>
            <Button label="Não" icon="pi pi-times" className="p-button-text" onClick={hideDeleteObjetoDialog} />
            <Button label="Sim" icon="pi pi-check" className="p-button-text" onClick={deleteObjeto} />
        </>
    ); 

    return (
        <div className="grid crud-demo">
            <div className="col-12">
                <div className="card">
                    <Toast ref={toast} />
                    <Toolbar className="mb-4" left={leftToolbarTemplate}></Toolbar>

                    <DataTable ref={dt} value={objetos}
                        dataKey="id" paginator rows={10} rowsPerPageOptions={[5, 10, 25]} className="datatable-responsive"
                        paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
                        currentPageReportTemplate="Mostrando {first} de {last}. Total de {totalRecords}"
                        globalFilter={globalFilter} emptyMessage="Sem objetos cadastrados." header={header} responsiveLayout="scroll">                        
                        <Column field="id" header="ID" sortable body={idBodyTemplate} headerStyle={{ width: '14%', minWidth: '10rem' }}></Column>
                        <Column field="descricaoCurta" header="Descrição curta" sortable body={descricaoCurtaBodyTemplate} headerStyle={{ width: '14%', minWidth: '10rem' }}></Column>
                        <Column field="descricaoDetalhada" header="Descrição Detalhada" body={descricaoDetalhadaBodyTemplate} headerStyle={{ width: '14%', minWidth: '10rem' }}></Column>
                        <Column body={rowData => {return <ColunaOpcoes rowData={rowData} editObjeto={editObjeto} confirmDeleteObjeto={confirmDeleteObjeto}/>}}></Column>
                    </DataTable>

                    <Dialog visible={objetoDialog} style={{ width: '450px' }} header="Cadastrar/Editar" modal className="p-fluid" footer={objetoDialogFooter} onHide={hideDialog}>
                        

                        <div className="field">
                            <label htmlFor="descricaoCurta">Descrição Curta</label>
                            <InputText id="descricaoCurta" value={objeto.descricaoCurta} onChange={(e) => onInputChange(e, 'descricaoCurta')} required autoFocus className={classNames({ 'p-invalid': submitted && !objeto.descricaoCurta })} />
                            {submitted && !objeto.descricaoCurta && <small className="p-invalid">Descrição Curta é Obrigatória.</small>}
                        </div>

                        <div className="field">
                            <label htmlFor="descricaoDetalhada">Descrição detalhada</label>
                            <InputTextarea id="descricaoDetalhada" value={objeto.descricaoDetalhada} onChange={(e) => onInputChange(e, 'descricaoDetalhada')} required autoFocus className={classNames({'p-invalid': submitted && !objeto.descricaoCurta})} rows={5} cols={30}/>
                            {submitted && !objeto.descricaoDetalhada && <small className='p-invalid'> Descrição detalhada é obrigatória</small>}
                        </div>

                        <div className="field">
                            <label htmlFor="valorCusto">Valor custo:</label>
                            <InputNumber id="valorCusto" value={objeto.valorCusto} onValueChange={(e) => onInputChange(e, 'valorCusto')} showButtons mode="currency" currency="BRL" />
                        </div>

                        <div className="field">
                            <label htmlFor="valorVenda">Valor venda:</label>
                            <InputNumber id="valorVenda" value={objeto.valorVenda} onValueChange={(e) => onInputChange(e, 'valorVenda')} showButtons mode='currency' currency="BRL" />
                        </div>

                        <div className="field">
                            <label htmlFor="marca">Marca</label>
                            <Dropdown id="marca" value={objeto.marca} onChange={(e) => onInputChange(e,'marca')} options={marcas} optionLabel='nome' placeholder="Selecione a marca"
                            filter valueTemplate={selectedMarcaTemplate} itemTemplate={marcaOptionTemplate} className="w-full md:w-14rem" >  </Dropdown>
                        </div>

                        <div className="field">
                            <label htmlFor='categoria'>Categoria</label>
                            <Dropdown id="categoria" value={objeto.categoria} onChange={(e) => onInputChange(e,'categoria')} options={categorias} optionLabel='nome' placeholder='Selecione a categoria'
                            filter valueTemplate={selectedCategoriaTemplate} itemTemplate={categoriaOptionTemplate} className="w-full md:w-14rem" > </Dropdown>
                        </div>

                    </Dialog>

                    <Dialog visible={objetoDeleteDialog} style={{ width: '450px' }} header="Confirmação" modal footer={deleteObjetoDialogFooter} onHide={hideDeleteObjetoDialog}>
                        <div className="flex align-items-center justify-content-center">
                            <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
                            {objeto && <span>Deseja Excluir?</span>}
                        </div>
                    </Dialog>


                </div>
            </div>
        </div>
    );
}

const comparisonFn = function (prevProps, nextProps) {
    return prevProps.location.pathname === nextProps.location.pathname;
};

export default React.memo(Produto, comparisonFn);