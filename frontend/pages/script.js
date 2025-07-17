function salvarFuncionario() {
  const nome = document.getElementById("nome").value.trim();
  const email = document.getElementById("email").value.trim();
  const empresa = document.getElementById("empresa").value.trim();
  const funcao = document.getElementById("funcao").value;
  const salario = document.getElementById("salario").value;
  const dia = document.getElementById("diaAdmissao").value;
  const mes = document.getElementById("mesAdmissao").value;
  const ano = document.getElementById("anoAdmissao").value;

  if (!nome || !email || !empresa || !funcao || !salario || !dia || !mes || !ano) {
    exibirMensagem("Preencha todos os campos!", "danger");
    return;
  }

  const funcionario = {
    nome,
    email,
    empresa,
    funcao,
    salario: parseFloat(salario).toFixed(2),
    admissao: `${dia.padStart(2, '0')}/${mes.padStart(2, '0')}/${ano}`
  };

  let lista = JSON.parse(localStorage.getItem("funcionarios") || "[]");
  const funcionarioEditando = JSON.parse(localStorage.getItem("funcionarioEditando"));

  if (funcionarioEditando && funcionarioEditando.index !== undefined) {
    lista[funcionarioEditando.index] = funcionario;
    localStorage.removeItem("funcionarioEditando");
  } else {
    lista.push(funcionario);
  }

  localStorage.setItem("funcionarios", JSON.stringify(lista));
  exibirMensagem("Funcionário salvo com sucesso!", "success");

  setTimeout(() => {
    window.location.href = "index.html";
  }, 1500);
}


// Função para exibir mensagem na tela
function exibirMensagem(texto, tipo) {
  let msg = document.getElementById("mensagem");
  if (!msg) {
    msg = document.createElement("div");
    msg.id = "mensagem";
    msg.style.margin = "10px 0";
    msg.style.padding = "10px";
    msg.style.borderRadius = "5px";
    msg.style.textAlign = "center";
    document.body.prepend(msg);
  }
  msg.textContent = texto;
  msg.style.background = tipo === "success" ? "#d4edda" : "#f8d7da";
  msg.style.color = tipo === "success" ? "#155724" : "#721c24";
}

function carregarFuncionarios() {
  const tabela = document.getElementById("tabelaUsuarios");
  const lista = JSON.parse(localStorage.getItem("funcionarios") || "[]");

  if (!tabela) return;

  tabela.innerHTML = "";

  for (const [index, f] of lista.entries()) {
    const linha = document.createElement("tr");
    linha.innerHTML = `
      <td>${f.nome}</td>
      <td>R$ ${f.salario}</td>
      <td>${f.funcao}</td>
      <td>${f.email}</td>
      <td>${f.admissao}</td>
      <td>${f.empresa}</td>
      <td>${index}</td>
      <td class="btn-acoes">
        
        <button class="btn btn-sm btn-warning" onclick="editarFuncionario(${index})">✏️</button>
        <button class="btn btn-sm btn-danger" onclick="excluirFuncionario(${index})">🗑️</button>
      </td>
    `;
    tabela.appendChild(linha);
  }
}

function excluirFuncionario(index) {
  if (confirm("Deseja excluir este funcionário?")) {
    const lista = JSON.parse(localStorage.getItem("funcionarios") || "[]");
    lista.splice(index, 1);
    localStorage.setItem("funcionarios", JSON.stringify(lista));
    carregarFuncionarios(); // Atualiza a tabela
  }
}

function editarFuncionario(index) {
  const lista = JSON.parse(localStorage.getItem("funcionarios") || "[]");
  const funcionario = lista[index];

  // Salva o funcionário e o índice temporariamente
  funcionario.index = index;
  localStorage.setItem("funcionarioEditando", JSON.stringify(funcionario));

  // Redireciona para o formulário de cadastro para edição
  window.location.href = "CadastroF.html";

}
window.onload = carregarFuncionarios;
