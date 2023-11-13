import {
  Box,
  Button,
  Checkbox,
  Input,
  Radio,
  RadioGroup,
  Select,
  SimpleGrid,
  Stack,
  Text,
} from "@chakra-ui/react";
import { useState } from "react";

interface Data {
  userName: string;
  dateOfBirth: string;
  weight: number;
  height: number;
  gender: string;
  exerciseTime: number;
}

const Dashboard = () => {
  const [history, setHistory] = useState("false");
  const [error, setError] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [isButtonEnabled, setIsButtonEnabled] = useState(false);
  const [buttonClicked, setButtonClicked] = useState(false);
  const [data, setData] = useState<Data>({
    userName: "",
    dateOfBirth: "",
    weight: null,
    height: null,
    gender: "",
    exerciseTime: null,
  });

  const convertDateToServerFormat = (dateString: any) => {
    const [year, month, day] = dateString.split("T")[0].split("-").map(Number);
    return {
      dia: day,
      mes: month,
      ano: year,
    };
  };

  const fetchData = async () => {
    setError("");
    setErrorMessage("");

    const dateOfBirthData = convertDateToServerFormat(data.dateOfBirth);

    try {
      const response = await fetch(
        "http://localhost:8080/profile/registration",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            name: data.userName,
            weight: data.weight,
            height: data.height,
            exerciseTime: data.exerciseTime,
            dateOfBirth: dateOfBirthData,
          }),
        }
      );

      if (!response.ok) {
        const errorData = await response.text();
        console.log(errorData);
        setError(errorData);
        throw new Error(errorData);
      }

      console.log("Dados do perfil enviados para o servidor com sucesso!");
    } catch (error) {
      console.error("Erro ao enviar dados: ", error.message);
    }
  };

  const handleButtonClick = () => {
    if (
      data?.userName &&
      data?.gender &&
      data?.weight &&
      data?.height &&
      data?.exerciseTime
    ) {
      fetchData();
    } else {
      setErrorMessage("Por favor, preencha todos os campos.");
    }
    setButtonClicked(true);
  };

  return (
    <Box backgroundColor="offwhite" h="100vh">
      <Box paddingTop="100px" marginX="310px" justifyContent="center">
        <Text
          fontFamily="poppins.400"
          fontSize="3xl"
          sx={{ textAlign: "center" }}
          marginBottom="70px"
        >
          Preencha as informações abaixo para continuar
        </Text>
        <SimpleGrid columns={2} spacing={10}>
          <Input
            placeholder="Nome"
            value={data.userName}
            onChange={(event: any) =>
              setData({ ...data, userName: event.target.value })
            }
          />
          <Input
            placeholder="Data de nascimento"
            type="datetime-local"
            value={data.dateOfBirth}
            onChange={(event: any) =>
              setData({ ...data, dateOfBirth: event.target.value })
            }
          />
          <Input
            placeholder="Peso (kg)"
            value={data.weight}
            onChange={(event: any) =>
              setData({ ...data, weight: event.target.value })
            }
          />
          <Input
            placeholder="Altura (cm)"
            value={data.height}
            onChange={(event: any) =>
              setData({ ...data, height: event.target.value })
            }
          />
          <Select
            height="40px"
            placeholder="Gênero"
            fontFamily="poppins.500"
            value={data.gender}
            onChange={(event: any) =>
              setData({ ...data, gender: event.target.value })
            }
          >
            <option value="M">Masculino</option>
            <option value="F">Feminino</option>
          </Select>
          <Input
            placeholder="Tempo médio de exercícios (em minutos)"
            value={data.exerciseTime}
            onChange={(event: any) =>
              setData({ ...data, exerciseTime: event.target.value })
            }
          />
        </SimpleGrid>

        <Text fontFamily="poppins.500" marginTop="40px">
          Doencas na familia?
        </Text>
        <RadioGroup onChange={setHistory} value={history} marginY="30px">
          <Stack>
            <Radio colorScheme="blackAlpha" size="lg" value={"false"}>
              Nao
            </Radio>
            <Radio colorScheme="blackAlpha" size="lg" value={"true"}>
              Sim
            </Radio>
          </Stack>
        </RadioGroup>
        {history === "true" && (
          <Input placeholder="Quais?" value="" onChange={() => {}} />
        )}
        <Text fontFamily="poppins.500" marginTop="40px" marginBottom="20px">
          Termos de uso:
        </Text>
        <Text color="gray">
          * Para obter um diagnóstico preciso e recomendações específicas, é
          importante consultar um médico. Além disso, se os sintomas persistirem
          ou piorarem, é essencial buscar atendimento médico imediatamente.
        </Text>
        <Box display="flex" justifyContent="space-between" marginTop="30px">
          <Checkbox
            size="lg"
            colorScheme="blackAlpha"
            borderColor="black"
            borderRadius="8px"
            defaultChecked
          >
            <Text fontSize="small">Li e concordo com os termos de uso</Text>
          </Checkbox>
          <Button
            color="brand.900"
            backgroundColor="black"
            width="150px"
            onClick={handleButtonClick}
          >
            Salvar
          </Button>
        </Box>
        {buttonClicked && errorMessage && (
          <Box py={2}>
            <Text color="red" fontSize="xs">
              {errorMessage}
            </Text>
          </Box>
        )}
        <Box py={2}>
          <Text color="red" fontSize="xs">
            {error}
          </Text>
        </Box>
      </Box>
    </Box>
  );
};

export default Dashboard;
