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
    alert("Preencha todos os campos!");
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

  const lista = JSON.parse(localStorage.getItem("funcionarios") || "[]");
  lista.push(funcionario);
  localStorage.setItem("funcionarios", JSON.stringify(lista));

  // Exibe mensagem de sucesso
  exibirMensagem("Usuário cadastrado com sucesso!", "success");

  // Redireciona após 1,5 segundos
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

  for (const f of lista) {
    const linha = document.createElement("tr");
    linha.innerHTML = `
      <td>${f.nome}</td>
      <td>R$ ${f.salario}</td>
      <td>${f.funcao}</td>
      <td>${f.email}</td>
      <td>${f.admissao}</td>
      <td>${f.empresa}</td>
      <td></td>
      <td class="btn-acoes">
        <button class="btn btn-sm btn-warning">✏️</button>
        <button class="btn btn-sm btn-danger">🗑️</button>
      </td>
    `;
    tabela.appendChild(linha);
    
  }
}

window.onload = carregarFuncionarios;
