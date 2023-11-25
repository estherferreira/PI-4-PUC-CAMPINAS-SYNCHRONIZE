import {
  Box,
  Button,
  Checkbox,
  IconButton,
  Input,
  Radio,
  RadioGroup,
  Select,
  SimpleGrid,
  Stack,
  Text,
} from "@chakra-ui/react";
import { useState } from "react";
import { useForm } from "react-hook-form";
import api from "../api";
import { useRouter } from "next/router";
import { InfoIcon } from "@chakra-ui/icons";

type FormValues = {
  userName: string;
  dateOfBirth: string;
  weight: string;
  height: string;
  gender: string;
  exerciseTime: string;
  diseaseHistory: string;
};

const Dashboard = () => {
  const router = useRouter();
  const [error, setError] = useState("");
  const [history, setHistory] = useState("false");
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<FormValues>();

  const convertDateToServerFormat = (dateString: any) => {
    const [year, month, day] = dateString.split("-").map(Number);
    return {
      dia: parseInt(day),
      mes: parseInt(month),
      ano: parseInt(year),
    };
  };

  const onSubmit = async (formData: FormValues) => {
    const dateOfBirth = convertDateToServerFormat(formData.dateOfBirth);
    const weight = parseInt(formData.weight);
    const height = parseInt(formData.height);
    const exerciseTime = parseInt(formData.exerciseTime);

    try {
      const response = await api.post("/profile/registration", {
        name: formData.userName,
        weight: weight,
        height: height,
        exerciseTime: exerciseTime,
        dateOfBirth: dateOfBirth,
        gender: formData.gender,
        diseaseHistory: history === "true" ? formData.diseaseHistory : "Não",
      });

      router.push("/dashboard");
    } catch (error) {
      if (error.response && error.response.data) {
        // Se existe uma mensagem de erro específica enviada pelo backend
        setError(error.response.data);
      } else {
        // Se o erro não contém uma resposta específica
        setError(
          "Ocorreu um erro ao enviar os dados. Por favor, tente novamente."
        );
        console.log(error);
      }
    }
  };

  return (
    <Box backgroundColor="offwhite" h="100vh">
      <Box paddingTop="100px" marginX="310px" justifyContent="center">
        <Box
          marginBottom="50px"
          backgroundColor="black"
          height="80px"
          borderRadius="8px"
          padding="20px"
          display="flex"
          justifyContent="space-between"
          alignItems="center"
        >
          <Box>
            <Text color="white" fontSize="14px" fontFamily="inter.400">
              Após completar seu cadastro, você será redirecionado para a página
              de login para entrar com os seus dados.
            </Text>
          </Box>
          <IconButton
            isRound={true}
            variant="solid"
            colorScheme="white"
            aria-label="Done"
            fontSize="20px"
            icon={<InfoIcon />}
          />
        </Box>

        <Text
          fontFamily="poppins.400"
          fontSize="3xl"
          sx={{ textAlign: "center" }}
          marginBottom="70px"
        >
          Preencha as informações abaixo para continuar
        </Text>
        <form onSubmit={handleSubmit(onSubmit)}>
          <SimpleGrid columns={2} spacing={10}>
            <Box>
              <Input
                h="50px"
                fontFamily="poppins.500"
                placeholder="Nome"
                {...register("userName", { required: "Digite um nome" })}
              />
              <Box py={2}>
                <Text color="red" fontSize="xs">
                  {errors?.userName && <>{errors?.userName.message}</>}
                </Text>
              </Box>
            </Box>
            <Box>
              <Input
                h="50px"
                fontFamily="poppins.500"
                placeholder="Data de nascimento"
                type="date"
                {...register("dateOfBirth", {
                  required: "Escolha uma data de nascimento",
                })}
              />
              <Box py={2}>
                <Text color="red" fontSize="xs">
                  {errors?.dateOfBirth && <>{errors?.dateOfBirth.message}</>}
                </Text>
              </Box>
            </Box>
            <Box>
              <Input
                h="50px"
                type="number"
                fontFamily="poppins.500"
                placeholder="Peso (kg)"
                {...register("weight", { required: "Digite seu peso", min: 1 })}
              />
              <Box py={2}>
                <Text color="red" fontSize="xs">
                  {errors?.weight && <>{errors?.weight.message}</>}
                  {errors?.weight && errors?.weight.type === "min" && (
                    <span>Valor inválido</span>
                  )}
                </Text>
              </Box>
            </Box>
            <Box>
              <Input
                h="50px"
                type="number"
                fontFamily="poppins.500"
                placeholder="Altura (cm)"
                {...register("height", {
                  required: "Digite sua altura",
                  min: 1,
                })}
              />
              <Box py={2}>
                <Text color="red" fontSize="xs">
                  {errors?.height && <>{errors?.height.message}</>}
                  {errors?.height && errors?.height.type === "min" && (
                    <span>Valor inválido</span>
                  )}
                </Text>
              </Box>
            </Box>
            <Box>
              <Select
                h="50px"
                fontFamily="poppins.500"
                placeholder="Gênero"
                {...register("gender", { required: "Selecione um gênero" })}
              >
                <option value="M">Masculino</option>
                <option value="F">Feminino</option>
              </Select>
              <Box py={2}>
                <Text color="red" fontSize="xs">
                  {errors?.gender && <>{errors?.gender.message}</>}
                </Text>
              </Box>
            </Box>
            <Box>
              <Input
                h="50px"
                type="number"
                fontFamily="poppins.500"
                placeholder="Tempo médio de exercícios (em minutos)"
                {...register("exerciseTime", {
                  required: "Digite o tempo médio de exercícios",
                  min: 0,
                })}
              />
              <Box py={2}>
                <Text color="red" fontSize="xs">
                  {errors?.exerciseTime && <>{errors?.exerciseTime.message}</>}
                  {errors?.exerciseTime &&
                    errors?.exerciseTime.type === "min" && (
                      <span>Valor inválido</span>
                    )}
                </Text>
              </Box>
            </Box>
          </SimpleGrid>

          <Text fontFamily="poppins.500" marginTop="40px">
            Doenças na familia?
          </Text>
          <RadioGroup onChange={setHistory} value={history} marginY="30px">
            <Stack>
              <Radio colorScheme="blackAlpha" size="lg" value={"false"}>
                Não
              </Radio>
              <Radio colorScheme="blackAlpha" size="lg" value={"true"}>
                Sim
              </Radio>
            </Stack>
          </RadioGroup>
          {history === "true" && (
            <Input
              h="50px"
              placeholder="Quais?"
              {...register("diseaseHistory")}
            />
          )}
          <Text fontFamily="poppins.500" marginTop="40px" marginBottom="20px">
            Termos de uso:
          </Text>
          <Text color="gray">
            * Para obter um diagnóstico preciso e recomendações específicas, é
            importante consultar um médico. Além disso, se os sintomas
            persistirem ou piorarem, é essencial buscar atendimento médico
            imediatamente.
          </Text>
          <Box display="flex" justifyContent="space-between" marginTop="30px">
            <Box>
              <Checkbox
                size="lg"
                colorScheme="blackAlpha"
                borderColor="black"
                borderRadius="8px"
                defaultChecked
              >
                <Text fontSize="small">Li e concordo com os termos de uso</Text>
              </Checkbox>
              <Text color="red" fontSize="xs">
                {error}
              </Text>
            </Box>
            <Button
              type="submit"
              color="brand.900"
              fontFamily="inter.500"
              backgroundColor="black"
              width="150px"
            >
              Salvar
            </Button>
          </Box>
        </form>
      </Box>
    </Box>
  );
};

export default Dashboard;
